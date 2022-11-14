package project.accountservice.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.Servlet;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String message = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        CustomErrorResponse body = new CustomErrorResponse(message, status, (ServletWebRequest) request);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<CustomErrorResponse> handleMethodArgumentNotValid(ConstraintViolationException ex, ServletWebRequest request) {
        String message = ex.getConstraintViolations()
                .stream()
                .limit(1)
                .map(ConstraintViolation::getMessageTemplate)
                .collect(Collectors.joining(""));
        CustomErrorResponse body = new CustomErrorResponse(message, HttpStatus.BAD_REQUEST, request);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
