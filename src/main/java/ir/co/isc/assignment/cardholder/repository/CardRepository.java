package ir.co.isc.assignment.cardholder.repository;

import ir.co.isc.assignment.cardholder.model.constant.CardType;
import ir.co.isc.assignment.cardholder.model.entity.CardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface CardRepository extends CrudRepository<CardEntity, Long> {

    @Query("select c" +
            " from CardEntity c join PersonEntity h on c.holder.nationalCode = h.nationalCode" +
            " join CardIssuerEntity i on c.issuer.iin = i.iin" +
            " join AccountEntity a on c.account.number = a.number" +
            " where" +
            " ( :holderNationalCode is null or h.nationalCode = :holderNationalCode ) and " +
            " ( :holderFirstName is null or h.firstName like concat('%', :holderFirstName, '%')) and " +
            " ( :holderLastName is null or h.lastName like concat('%', :holderLastName, '%')) and " +
            " ( :fromExpireLocalDate is null or c.expireDate >= :fromExpireLocalDate ) and " +
            " ( :toExpireLocalDate is null or c.expireDate <= :toExpireLocalDate ) and " +
            " ( :type is null or c.type = :type )" )
    Page<CardEntity> findAllByCriteria(@Param("holderNationalCode") String holderNationalCode,
                                       @Param("holderFirstName") String holderFirstName,
                                       @Param("holderLastName") String holderLastName,
                                       @Param("fromExpireLocalDate") LocalDate fromExpireLocalDate,
                                       @Param("toExpireLocalDate") LocalDate toExpireLocalDate,
                                       @Param("type") CardType type,
                                       Pageable pageable);
}
