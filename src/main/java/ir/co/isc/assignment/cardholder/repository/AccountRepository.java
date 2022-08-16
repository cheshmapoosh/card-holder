package ir.co.isc.assignment.cardholder.repository;

import ir.co.isc.assignment.cardholder.model.entity.AccountEntity;
import ir.co.isc.assignment.cardholder.model.entity.PersonEntity;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<AccountEntity, String> {
}
