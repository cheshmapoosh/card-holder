package ir.co.isc.assignment.cardholder.service;

import ir.co.isc.assignment.cardholder.model.constant.CardType;
import ir.co.isc.assignment.cardholder.model.entity.CardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface CardService {
    Page<CardEntity> findAllByCriteria(String holderNationalCode,
                                       String holderFirstName,
                                       String holderLastName,
                                       LocalDate fromExpireLocalDate,
                                       LocalDate toExpireLocalDate,
                                       CardType type,
                                       Pageable pageable);

    CardEntity save(CardEntity card);
}
