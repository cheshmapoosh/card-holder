package ir.co.isc.assignment.cardholder.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
public class AccountEntity {
    @Id
    @Column(length=10, nullable = false)
    private String number;
}
