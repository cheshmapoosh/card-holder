package ir.co.isc.assignment.cardholder.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@Entity
public class PersonEntity {
    @Id
    @Column(length = 10, nullable = false)
    private String nationalCode;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(length = 13)
    private String callNumber;

    private String address;

    @OneToMany(mappedBy = "holder")
    private List<CardEntity> cardEntities;

}
