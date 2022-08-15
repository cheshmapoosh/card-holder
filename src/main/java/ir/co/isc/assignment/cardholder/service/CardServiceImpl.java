package ir.co.isc.assignment.cardholder.service;

import ir.co.isc.assignment.cardholder.model.constant.CardType;
import ir.co.isc.assignment.cardholder.model.entity.CardEntity;
import ir.co.isc.assignment.cardholder.repository.CardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CardServiceImpl implements CardService {
    private final CardRepository cardRepository;

    public CardServiceImpl(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public Page<CardEntity> findAllByCriteria(String holderNationalCode,
                                              String holderFirstName,
                                              String holderLastName,
                                              LocalDate fromExpireLocalDate,
                                              LocalDate toExpireLocalDate,
                                              CardType type,
                                              Pageable pageable) {
        return cardRepository.findAllByCriteria(holderNationalCode, holderFirstName, holderLastName, fromExpireLocalDate, toExpireLocalDate, type, pageable);
    }
}
