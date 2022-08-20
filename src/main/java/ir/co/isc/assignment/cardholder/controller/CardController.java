package ir.co.isc.assignment.cardholder.controller;

import ir.co.isc.assignment.cardholder.exception.DuplicateDataException;
import ir.co.isc.assignment.cardholder.model.constant.CardType;
import ir.co.isc.assignment.cardholder.model.constant.SortDirection;
import ir.co.isc.assignment.cardholder.model.constant.SortField;
import ir.co.isc.assignment.cardholder.model.dto.CardDto;
import ir.co.isc.assignment.cardholder.model.dto.CardSearchDto;
import ir.co.isc.assignment.cardholder.model.dto.PageDto;
import ir.co.isc.assignment.cardholder.model.entity.CardEntity;
import ir.co.isc.assignment.cardholder.model.mapper.CardDtoMapper;
import ir.co.isc.assignment.cardholder.service.CardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/card")
public class CardController {

    private final CardService cardService;
    private final CardDtoMapper cardDtoMapper;

    public CardController(CardService cardService,
                          CardDtoMapper cardDtoMapper) {
        this.cardService = cardService;
        this.cardDtoMapper = cardDtoMapper;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    PageDto<CardDto> findAllByCriteria(@Valid @RequestBody CardSearchDto cardSearchDto) {
        final DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern("yyyy/MM")
                .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
                .toFormatter();
        LocalDate fromExpireLocalDate = Optional.ofNullable(cardSearchDto.getFromExpireDate())
                .map(from -> LocalDate.parse(from, formatter).plusMonths(1).minusDays(1))
                .orElse(null);
        LocalDate toExpireLocalDate = Optional.ofNullable(cardSearchDto.getToExpireDate())
                .map(to -> LocalDate.parse(to, formatter).plusMonths(1).minusDays(1))
                .orElse(null);
        PageRequest pageRequest = PageRequest.of(cardSearchDto.getPage(), cardSearchDto.getSize(),
                cardSearchDto.getSortDirection() == SortDirection.ASC ?
                        Sort.by(cardSearchDto.getSortField().getType()).ascending() :
                        Sort.by(cardSearchDto.getSortField().getType()).descending());
        Page<CardEntity> cardEntityPage = cardService.findAllByCriteria(cardSearchDto.getHolderNationalCode(),
                cardSearchDto.getHolderFirstName(),
                cardSearchDto.getHolderLastName(),
                fromExpireLocalDate,
                toExpireLocalDate,
                cardSearchDto.getType(),
                pageRequest);

        long total = cardEntityPage.getTotalElements();
        if (total == 0) {
            log.warn(String.format("No cards were found matching criteria[%s]", cardSearchDto));
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No cards were found matching your criteria");
        }

        int size = cardEntityPage.getNumberOfElements();
        log.info(String.format("Fetch %d of %d card by criteria[%s]", size, total, cardSearchDto));
        List<CardDto> cardDtos = cardDtoMapper.mapToCardDtos(cardEntityPage.getContent());
        return PageDto.<CardDto>builder()
                .page(cardEntityPage.getNumber())
                .size(size)
                .total(total)
                .content(cardDtos)
                .build();
    }

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public CardDto save(@RequestBody CardDto cardDto) {
        CardEntity card = cardDtoMapper.mapToCardEntity(cardDto);
        try {
            card = cardService.save(card);
            return cardDtoMapper.mapToCardDto(card);
        } catch (DuplicateDataException e) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, e.getMessage());

        }
    }
}
