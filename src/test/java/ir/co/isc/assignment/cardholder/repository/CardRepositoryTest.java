package ir.co.isc.assignment.cardholder.repository;

import ir.co.isc.assignment.cardholder.model.dto.CardDto;
import ir.co.isc.assignment.cardholder.model.entity.CardEntity;
import ir.co.isc.assignment.cardholder.model.mapper.CardDtoMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static ir.co.isc.assignment.cardholder.model.constant.CardType.DEBIT;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CardRepositoryTest {


    @Autowired
    CardRepository cardRepository;

    @Autowired
    CardDtoMapper cardDtoMapper;

    @Test
    void save_one_successful() {
        CardDto cardDto = new CardDto();
        cardDto.setNumber("6104332841246571");
        cardDto.setType(DEBIT);
        cardDto.setExpireDate("2025/01");
        cardDto.setEnable(Boolean.TRUE);
        cardDto.setIssuerIin("603799");
        cardDto.setIssuerName("MELLI");
        cardDto.setHolderNationalCode("5099553691");
        cardDto.setHolderFirstName("Abbas8");
        cardDto.setHolderFirstName("Cheshmapoosh8");
        cardDto.setHolderCallNumber("09121173691");
        cardDto.setHolderAddress("Tehran");
        cardDto.setAccountNumber("1221520592");
        CardEntity cardEntity = cardRepository.save(cardDtoMapper.mapToCardEntity(cardDto));

        assertNotNull(cardEntity);
        assertNotNull(cardEntity.getId());
    }
}