package ir.co.isc.assignment.cardholder.model.entity;

import ir.co.isc.assignment.cardholder.model.constant.CardType;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Data
@ToString
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"type", "issuer_iin", "holder_ncod"})})
public class CardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CARD_SEQ")
    private Long id;
    private String externalId;
    @Column(length = 16, nullable = false, unique = true)
    private String number;
    private CardType type;
    private LocalDate expireDate;
    private Boolean enable;
    @ManyToOne
    @JoinColumn(name = "issuer_iin", nullable = false)
    private CardIssuerEntity issuer;
    @ManyToOne
    @JoinColumn(name = "holder_ncod", nullable = false)
    private PersonEntity holder;
    @ManyToOne
    @JoinColumn(name = "account_num", nullable = false)
    private AccountEntity account;
}
