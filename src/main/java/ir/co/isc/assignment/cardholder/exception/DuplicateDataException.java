package ir.co.isc.assignment.cardholder.exception;

import org.springframework.dao.DataIntegrityViolationException;

public class DuplicateDataException extends CardHolderException {
    public DuplicateDataException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
