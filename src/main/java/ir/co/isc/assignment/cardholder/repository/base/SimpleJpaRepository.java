package ir.co.isc.assignment.cardholder.repository.base;

import org.apache.commons.lang3.Validate;

import javax.persistence.EntityManager;
import java.util.Optional;

public class SimpleJpaRepository<T, ID> implements CrudRepository<T, ID> {

    private static final String ID_MUST_NOT_BE_NULL = "The given id must not be null!";

    private final EntityManager em;


    public SimpleJpaRepository(EntityManager entityManager) {

        Validate.notNull(entityManager, "EntityManager must not be null!");

        this.em = entityManager;
    }


	@Override
	public <S extends T> S save(S entity) {
		return null;
	}

	@Override
	public <S extends T> Iterable<S> saveAll(Iterable<S> entities) {
		return null;
	}

	@Override
	public Optional<T> findById(ID id) {
		return Optional.empty();
	}

	@Override
	public boolean existsById(ID id) {
		return false;
	}

	@Override
	public Iterable<T> findAll() {
		return null;
	}

	@Override
	public Iterable<T> findAllById(Iterable<ID> ids) {
		return null;
	}

	@Override
	public long count() {
		return 0;
	}

	@Override
	public void deleteById(ID id) {

	}

	@Override
	public void delete(T entity) {

	}

	@Override
	public void deleteAllById(Iterable<? extends ID> ids) {

	}

	@Override
	public void deleteAll(Iterable<? extends T> entities) {

	}

	@Override
	public void deleteAll() {

	}
}