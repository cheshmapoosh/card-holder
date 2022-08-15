package ir.co.isc.assignment.cardholder.model.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class PersonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PERSON_SEQ")
    private Long id;
    @Column(length = 10, nullable = false, unique = true)
    private String nationalCode;
    private String callNumber;
    private String address;

}
