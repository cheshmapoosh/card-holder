package ir.co.isc.assignment.cardholder.model.dto;

import ir.co.isc.assignment.cardholder.model.constant.CardType;
import lombok.Data;

@Data
public class CardDto {
    private String number;
    private CardType type;
    private String expireDate;
    private Boolean enable;
    private String issuerIin;
    private String issuerName;
    private String holderNationalCode;
    private String holderFirstName;
    private String holderLastName;
    private String holderCallNumber;
    private String holderAddress;
    private String accountNumber;
}
