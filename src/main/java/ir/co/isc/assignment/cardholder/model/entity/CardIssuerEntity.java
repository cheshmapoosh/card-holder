package ir.co.isc.assignment.cardholder.model.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class CardIssuerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CARD_ISSUER_SEQ")
    private Long id;
    @Column(length=6, nullable=false, unique=true)
    private String code;
    private String name;

}
