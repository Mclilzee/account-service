package project.accountservice.exception;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<CustomErrorMessage> handleConstraintViolation(
            ConstraintViolationException e, WebRequest request) {
        CustomErrorMessage body = new CustomErrorMessage(HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                "User exist!");
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
