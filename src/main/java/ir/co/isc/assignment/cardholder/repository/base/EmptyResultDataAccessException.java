package ir.co.isc.assignment.cardholder.repository.base;

public class EmptyResultDataAccessException extends DataAccessException {
    private final int expectedSize;

    public EmptyResultDataAccessException(String msg, int expectedSize) {
        super(msg);
        this.expectedSize = expectedSize;
    }

    public EmptyResultDataAccessException(String msg, int expectedSize, Throwable ex) {
        super(msg, ex);
        this.expectedSize = expectedSize;
    }
}
