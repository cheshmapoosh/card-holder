package ir.co.isc.assignment.cardholder.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
public class CardIssuerEntity {
    @Id
    @Column(length=6)
    private String iin;
    private String name;

}
