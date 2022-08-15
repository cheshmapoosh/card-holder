package ir.co.isc.assignment.cardholder.model.entity;

import ir.co.isc.assignment.cardholder.model.constant.CardType;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@ToString
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "type", "issuer_id", "holder_id" }) })
public class CardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CARD_SEQ")
    private Long id;
    @Column(length = 16, nullable = false, unique = true)
    private String number;
    private CardType type;
    private LocalDate expireDate;
    private Boolean enable;
    @ManyToOne
    @JoinColumn(name = "issuer_id", nullable = false)
    private CardIssuerEntity issuer;
    @ManyToOne
    @JoinColumn(name = "holder_id", nullable = false)
    private PersonEntity holder;
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private AccountEntity account;
}
