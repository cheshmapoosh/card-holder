package ir.co.isc.assignment.cardholder.model.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACCOUNT_SEQ")
    private Long id;
    @Column(length=10, nullable=false, unique=true)
    private String number;
}
