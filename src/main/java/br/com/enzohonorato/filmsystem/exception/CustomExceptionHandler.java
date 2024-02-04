package br.com.enzohonorato.filmsystem.exception;

import br.com.enzohonorato.filmsystem.exception.details.BadRequestExceptionDetails;
import br.com.enzohonorato.filmsystem.exception.details.ConstraintViolationExceptionDetails;
import br.com.enzohonorato.filmsystem.exception.details.FieldMessage;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ConstraintViolationExceptionDetails> constraintViolationExceptionHandler(ConstraintViolationException cve) {

        List<FieldMessage> fieldMessages= new ArrayList<>();

        for(ConstraintViolation cv: cve.getConstraintViolations()) {
            fieldMessages.add(new FieldMessage(cv.getPropertyPath().toString(), cv.getMessage()));
        }

        return new ResponseEntity<>(ConstraintViolationExceptionDetails.builder()
                .title("Validation error")
                .fieldMessages(fieldMessages)
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BadRequestExceptionDetails> badRequestExceptionHandler(BadRequestException bre) {
        return new ResponseEntity<>(
                BadRequestExceptionDetails.builder()
                        .title("Bad Request Exception")
                        .status(HttpStatus.BAD_REQUEST.value())
                        .details(bre.getMessage())
                        .developerMessage(bre.getClass().getName())
                        .timeStamp(LocalDateTime.now()).build(), HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<BadRequestExceptionDetails> sqlIntegrityConstraintViolationExceptionHandler(Exception exception) {
        return new ResponseEntity<>(
                BadRequestExceptionDetails.builder()
                        .title("Bad Request Exception")
                        .status(HttpStatus.BAD_REQUEST.value())
                        .details(exception.getMessage())
                        .developerMessage(exception.getClass().getName())
                        .timeStamp(LocalDateTime.now()).build(), HttpStatus.BAD_REQUEST
        );
    }


}
