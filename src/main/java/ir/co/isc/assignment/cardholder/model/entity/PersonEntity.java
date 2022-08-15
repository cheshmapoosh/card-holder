package ir.co.isc.assignment.cardholder.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@Entity
public class PersonEntity {
    @Id
    @Column(length = 10)
    private String nationalCode;
    private String firstName;
    private String lastName;
    private String callNumber;
    private String address;
    @OneToMany(mappedBy = "holder")
    private List<CardEntity> cardEntities;

}
