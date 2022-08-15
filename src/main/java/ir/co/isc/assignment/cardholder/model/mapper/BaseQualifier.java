package ir.co.isc.assignment.cardholder.model.mapper;

import ir.co.isc.assignment.cardholder.exception.DataMappingException;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface BaseQualifier {
    @Named("formatUuid")
    default String formatUrl(UUID uuid) {
        if (uuid == null) {
            return null;
        }
        return uuid.toString();
    }

    @Named("parseUuid")
    default UUID parseUuid(String uuid) {
        if (uuid == null) {
            return null;
        }
        try {
            return UUID.fromString(uuid);
        } catch (IllegalArgumentException e) {
            throw new DataMappingException("The value for parse uuid not valid [" + uuid + "]", e);
        }
    }

    @Named("formatYearMonth")
    default String formatYearMonth(LocalDate yearMonth) {
        try {
            return Optional.ofNullable(yearMonth).map(DateTimeFormatter.ofPattern("yyyy/MM")::format).orElse(null);
        } catch (DateTimeException e) {
            throw new DataMappingException("The value for format LocalDate(with pattern of yyyy/MM) not valid [" + yearMonth + "]", e);
        }
    }


    @Named("parseYearMonth")
    default LocalDate parseYearMonth(String yearMonth) {
        try {
            final DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                    .appendPattern("yyyy/MM")
                    .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
                    .toFormatter();
            return Optional.ofNullable(yearMonth).map(ym -> LocalDate.parse(ym, formatter).plusMonths(1).minusDays(1)).orElse(null);
        } catch (DateTimeException e) {
            throw new DataMappingException("The value for parse LocalDate(with pattern of yyyy/MM) not valid [" + yearMonth + "]", e);
        }
    }
}
