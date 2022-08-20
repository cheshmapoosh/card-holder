package ir.co.isc.assignment.cardholder.model.dto;

import ir.co.isc.assignment.cardholder.model.constant.CardType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardDto {
    @NotNull
    @Pattern(regexp = "^([0-9]){16}$")
    private String number;

    @NotNull
    private CardType type;

    @NotNull
    @Pattern(regexp = "^\\d{2}/(0[1-9]|1[0-2])$")
    private String expireDate;

    @NotNull
    @Builder.Default
    private Boolean enable = true;

    @NotNull
    @Pattern(regexp = "^([0-9]){16}$")
    private String issuerIin;

    @NotNull
    @Size(min = 3)
    private String issuerName;

    @NotNull
    @Pattern(regexp = "^([0-9]){10}$")
    private String holderNationalCode;

    @NotNull
    @Size(min = 3)
    private String holderFirstName;

    @NotNull
    @Size(min = 3)
    private String holderLastName;

    @Pattern(regexp = "^([0-9]){10}$")
    private String holderCallNumber;

    @Size(min = 5)
    private String holderAddress;

    @NotNull
    @Pattern(regexp = "^([0-9]){10}$")
    private String accountNumber;
}
