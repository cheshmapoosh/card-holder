package ir.co.isc.assignment.cardholder.service;

import ir.co.isc.assignment.cardholder.exception.DuplicateDataException;
import ir.co.isc.assignment.cardholder.model.constant.CardType;
import ir.co.isc.assignment.cardholder.model.entity.AccountEntity;
import ir.co.isc.assignment.cardholder.model.entity.CardEntity;
import ir.co.isc.assignment.cardholder.model.entity.CardIssuerEntity;
import ir.co.isc.assignment.cardholder.model.entity.PersonEntity;
import ir.co.isc.assignment.cardholder.repository.AccountRepository;
import ir.co.isc.assignment.cardholder.repository.CardIssuerRepository;
import ir.co.isc.assignment.cardholder.repository.CardRepository;
import ir.co.isc.assignment.cardholder.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CardServiceImpl implements CardService {
    private final PersonRepository personRepository;

    private final CardIssuerRepository cardIssuerRepository;

    private final AccountRepository accountRepository;

    private final CardRepository cardRepository;

    public CardServiceImpl(PersonRepository personRepository,
                           CardIssuerRepository cardIssuerRepository,
                           AccountRepository accountRepository,
                           CardRepository cardRepository) {
        this.personRepository = personRepository;
        this.cardIssuerRepository = cardIssuerRepository;
        this.accountRepository = accountRepository;
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

    @Override
    public CardEntity save(CardEntity card) {
        PersonEntity holder = personRepository.save(card.getHolder());
        CardIssuerEntity issuer = cardIssuerRepository.save(card.getIssuer());
        AccountEntity account =  accountRepository.save(card.getAccount());
        card.setHolder(holder);
        card.setIssuer(issuer);
        card.setAccount(account);
        try {
            return cardRepository.save(card);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateDataException("Card is duplicate", e);
        }
    }
}
