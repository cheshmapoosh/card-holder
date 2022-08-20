package ir.co.isc.assignment.cardholder.model.dto;

import ir.co.isc.assignment.cardholder.model.constant.CardType;
import ir.co.isc.assignment.cardholder.model.constant.SortDirection;
import ir.co.isc.assignment.cardholder.model.constant.SortField;
import lombok.*;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CardSearchDto {

    @Pattern(regexp = "^([0-9]){10}$")
    private String holderNationalCode;

    @Size(min = 3)
    private String holderFirstName;

    @Size(min = 3)
    private String holderLastName;

    @Pattern(regexp = "^\\d{2}/(0[1-9]|1[0-2])$")
    private String fromExpireDate;


    @Pattern(regexp = "^\\d{2}/(0[1-9]|1[0-2])$")
    private String toExpireDate;

    private CardType type;

    @NotNull
    @Builder.Default
    private SortField sortField = SortField.HOLDER_NATIONAL_CODE;

    @NotNull
    @Builder.Default
    private SortDirection sortDirection = SortDirection.DESC;

    @Min(0)
    @NotNull
    @Builder.Default
    private Integer page = 0;

    @Min(1)
    @Max(100)
    @NotNull
    @Builder.Default
    private Integer size = 5;
}
