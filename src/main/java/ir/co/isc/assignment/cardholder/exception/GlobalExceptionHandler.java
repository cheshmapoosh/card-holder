package ir.co.isc.assignment.cardholder.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler
    public void argumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage());
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
    }
}