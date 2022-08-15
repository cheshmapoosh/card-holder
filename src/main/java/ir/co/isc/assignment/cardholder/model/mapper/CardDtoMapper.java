package ir.co.isc.assignment.cardholder.model.mapper;


import ir.co.isc.assignment.cardholder.model.dto.CardDto;
import ir.co.isc.assignment.cardholder.model.entity.CardEntity;
import java.util.Collections;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {BaseQualifier.class})
public interface CardDtoMapper {

    @Mapping(target = "expireDate", qualifiedByName="formatYearMonth")
    @Mapping(target = "issuerIin", source = "issuer.iin")
    @Mapping(target = "issuerName", source = "issuer.name")
    @Mapping(target = "accountNumber", source = "account.number")
    @Mapping(target = "holderNationalCode", source = "holder.nationalCode")
    @Mapping(target = "holderFirstName", source = "holder.firstName")
    @Mapping(target = "holderLastName", source = "holder.lastName")
    @Mapping(target = "holderCallNumber", source = "holder.callNumber")
    @Mapping(target = "holderAddress", source = "holder.address")
    CardDto mapToCardDto (CardEntity cardEntity);

    @Mapping(target = "expireDate", source = "expireDate", qualifiedByName="parseYearMonth")
    @Mapping(target = "issuer.iin", source = "issuerIin")
    @Mapping(target = "issuer.name", source = "issuerName")
    @Mapping(target = "account.number", source = "accountNumber")
    @Mapping(target = "holder.nationalCode", source = "holderNationalCode")
    @Mapping(target = "holder.firstName", source = "holderFirstName")
    @Mapping(target = "holder.lastName", source = "holderLastName")
    @Mapping(target = "holder.callNumber", source = "holderCallNumber")
    @Mapping(target = "holder.address", source = "holderAddress")
    CardEntity mapToCardEntity (CardDto cardDto);

    @AfterMapping
    default void fillExternalIdWithRandomUuid(@MappingTarget CardEntity cardEntity) {
        cardEntity.setExternalId(UUID.randomUUID().toString());
    }

    default List<CardDto> mapToCardDtos(List<CardEntity> content) {
        if (CollectionUtils.isEmpty(content)) {
            return Collections.emptyList();
        }
        return content.stream()
                .map(this::mapToCardDto)
                .collect(Collectors.toList());
    }
}
