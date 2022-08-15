package ir.co.isc.assignment.cardholder.exception;

public class CardHolderException extends RuntimeException {

    public CardHolderException() {
    }

    public CardHolderException(String message) {
        super(message);
    }

    public CardHolderException(String message, Throwable cause) {
        super(message, cause);
    }

    public CardHolderException(Throwable cause) {
        super(cause);
    }

    public CardHolderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
