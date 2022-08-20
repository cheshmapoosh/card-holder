package ir.co.isc.assignment.cardholder.repository;

import ir.co.isc.assignment.cardholder.model.dto.CardDto;
import ir.co.isc.assignment.cardholder.model.entity.CardEntity;
import ir.co.isc.assignment.cardholder.model.mapper.CardDtoMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import static ir.co.isc.assignment.cardholder.model.constant.CardType.DEBIT;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CardRepositoryTest {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    CardIssuerRepository cardIssuerRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    CardDtoMapper cardDtoMapper;

    @Test
    void saveOne_expectedSuccessful() {
        CardDto cardDto = new CardDto();
        cardDto.setNumber("6104332841246571");
        cardDto.setType(DEBIT);
        cardDto.setExpireDate("2025/01");
        cardDto.setEnable(Boolean.TRUE);
        cardDto.setIssuerIin("603799");
        cardDto.setIssuerName("MELLI");
        cardDto.setHolderNationalCode("3979277858");
        cardDto.setHolderFirstName("Ali");
        cardDto.setHolderFirstName("Dosti");
        cardDto.setHolderCallNumber("09121173691");
        cardDto.setHolderAddress("Tehran");
        cardDto.setAccountNumber("1221520592");
        CardEntity card = cardDtoMapper.mapToCardEntity(cardDto);

        personRepository.save(card.getHolder());
        cardIssuerRepository.save(card.getIssuer());
        accountRepository.save(card.getAccount());
        cardRepository.save(card);

        assertNotNull(card);
        assertNotNull(card.getId());
    }

    @Test
    void save_duplicateHolderAndIssuerAndCardType_expectedUnsuccessful() {
        CardDto cardDto = new CardDto();
        cardDto.setNumber("6104332841246581");
        cardDto.setType(DEBIT);
        cardDto.setExpireDate("2029/01");
        cardDto.setEnable(Boolean.TRUE);
        cardDto.setIssuerIin("603799");
        cardDto.setIssuerName("MELLI");
        cardDto.setHolderNationalCode("3979297858");
        cardDto.setHolderFirstName("Ali1");
        cardDto.setHolderFirstName("Dosti1");
        cardDto.setHolderCallNumber("09121173691");
        cardDto.setHolderAddress("Tehran");
        cardDto.setAccountNumber("1221520592");
        CardEntity card = cardDtoMapper.mapToCardEntity(cardDto);

        personRepository.save(card.getHolder());
        cardIssuerRepository.save(card.getIssuer());
        accountRepository.save(card.getAccount());
        cardRepository.save(card);

        CardDto cardDto2 = new CardDto();
        cardDto2.setNumber("6104339841246581");
        cardDto2.setType(DEBIT);
        cardDto2.setExpireDate("2029/01");
        cardDto2.setEnable(Boolean.TRUE);
        cardDto2.setIssuerIin("603799");
        cardDto2.setIssuerName("MELLI");
        cardDto2.setHolderNationalCode("3979297858");
        cardDto2.setHolderFirstName("Ali1");
        cardDto2.setHolderFirstName("Dosti1");
        cardDto2.setHolderCallNumber("09121173691");
        cardDto2.setHolderAddress("Tehran");
        cardDto2.setAccountNumber("1221520592");
        CardEntity card2 = cardDtoMapper.mapToCardEntity(cardDto2);

        personRepository.save(card2.getHolder());
        cardIssuerRepository.save(card2.getIssuer());
        accountRepository.save(card2.getAccount());
        assertThrows(DataIntegrityViolationException.class, () -> cardRepository.save(card2));
    }
}