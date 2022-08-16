package ir.co.isc.assignment.cardholder.controller;

import ir.co.isc.assignment.cardholder.model.constant.CardType;
import ir.co.isc.assignment.cardholder.model.constant.SortDirection;
import ir.co.isc.assignment.cardholder.model.constant.SortField;
import ir.co.isc.assignment.cardholder.model.dto.CardDto;
import ir.co.isc.assignment.cardholder.model.dto.PageDto;
import ir.co.isc.assignment.cardholder.model.entity.CardEntity;
import ir.co.isc.assignment.cardholder.model.mapper.CardDtoMapper;
import ir.co.isc.assignment.cardholder.service.CardService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/card")
//@Validated
public class CardController {

    private final CardService cardService;
    private final CardDtoMapper cardDtoMapper;

    public CardController(CardService cardService,
                          CardDtoMapper cardDtoMapper) {
        this.cardService = cardService;
        this.cardDtoMapper = cardDtoMapper;
    }

    @GetMapping
    PageDto<CardDto> findAllByCriteria(
            @RequestParam(required = false) @Pattern(regexp = "^[^<>%\\-@+$|='\"]*$") String holderNationalCode,
            @RequestParam(required = false) @Pattern(regexp = "^[^<>%\\-@+$|='\"]*$") String holderFirstName,
            @RequestParam(required = false) @Pattern(regexp = "^[^<>%\\-@+$|='\"]*$") String holderLastName,
            @RequestParam(required = false) @Pattern(regexp = "^[^<>%\\-@+$|='\"]*$") String fromExpireDate,
            @RequestParam(required = false) @Pattern(regexp = "^[^<>%\\-@+$|='\"]*$") String toExpireDate,
            @RequestParam(required = false) CardType type,
            @RequestParam(required = false, defaultValue = "HOLDER_NATIONAL_CODE") SortField sortField,
            @RequestParam(required = false, defaultValue = "DESC") SortDirection sortDirection,
            @RequestParam(required = false, defaultValue = "0") @Min(0) Integer page,
            @RequestParam(required = false, defaultValue = "5") @Min(1) @Max(100) Integer size
    ) {
        final DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern("yyyy/MM")
                .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
                .toFormatter();
        LocalDate fromExpireLocalDate = Optional.ofNullable(fromExpireDate)
                .map(from -> LocalDate.parse(from, formatter).plusMonths(1).minusDays(1))
                .orElse(null);
        LocalDate toExpireLocalDate = Optional.ofNullable(toExpireDate)
                .map(to -> LocalDate.parse(to, formatter).plusMonths(1).minusDays(1))
                .orElse(null);
        PageRequest pageRequest = PageRequest.of(page, size,
                sortDirection == SortDirection.ASC ?
                        Sort.by(sortField.getType()).ascending() :
                        Sort.by(sortField.getType()).descending());
        Page<CardEntity> cardEntityPage = cardService.findAllByCriteria(holderNationalCode,
                holderFirstName,
                holderLastName,
                fromExpireLocalDate,
                toExpireLocalDate,
                type,
                pageRequest);

        if (cardEntityPage.getTotalElements() == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No cards were found matching your criteria");
        }

        List<CardDto> cardDtos = cardDtoMapper.mapToCardDtos(cardEntityPage.getContent());
        return PageDto.<CardDto>builder()
                .page(cardEntityPage.getNumber())
                .size(cardEntityPage.getNumberOfElements())
                .total(cardEntityPage.getTotalElements())
                .content(cardDtos)
                .build();
    }
}
