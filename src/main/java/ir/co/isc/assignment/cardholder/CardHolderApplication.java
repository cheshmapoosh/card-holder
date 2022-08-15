package ir.co.isc.assignment.cardholder;

import ir.co.isc.assignment.cardholder.model.constant.CardType;
import ir.co.isc.assignment.cardholder.model.entity.AccountEntity;
import ir.co.isc.assignment.cardholder.model.entity.CardEntity;
import ir.co.isc.assignment.cardholder.model.entity.CardIssuerEntity;
import ir.co.isc.assignment.cardholder.model.entity.PersonEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;

public class CardHolderApplication {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("CardHolders");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();

        CardIssuerEntity issuer = new CardIssuerEntity();
        issuer.setCode("610433");
        issuer.setName("Mellat");
        entityManager.persist(issuer);

        PersonEntity holder = new PersonEntity();
        holder.setAddress("Tehran");
        holder.setCallNumber("09121173696");
        holder.setNationalCode("5099553656");
        entityManager.persist(holder);

        AccountEntity account = new AccountEntity();
        account.setNumber("1262215204");
        entityManager.persist(account);

        CardEntity cardEntity = new CardEntity();
        cardEntity.setExpireDate(LocalDate.now().plusYears(1));
        cardEntity.setEnable(true);
        cardEntity.setNumber("6104545646546546");
        cardEntity.setType(CardType.CREDIT);
        cardEntity.setIssuer(issuer);
        cardEntity.setHolder(holder);
        cardEntity.setAccount(account);
        entityManager.persist(cardEntity);

        entityManager.getTransaction().commit();

        CardEntity existCardEntity = entityManager.find(CardEntity.class, cardEntity.getId());
        entityManager.close();
        entityManagerFactory.close();

        System.out.println(existCardEntity);
    }
}
