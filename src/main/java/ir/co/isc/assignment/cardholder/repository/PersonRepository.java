package ir.co.isc.assignment.cardholder.repository;

import ir.co.isc.assignment.cardholder.model.constant.CardType;
import ir.co.isc.assignment.cardholder.model.entity.CardEntity;
import ir.co.isc.assignment.cardholder.model.entity.PersonEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface PersonRepository extends CrudRepository<PersonEntity, String> {
}
