package com.david.addressbook.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class CommonExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { ConstraintViolationException.class})
    protected ResponseEntity<Object> handleValidationExceptions(ConstraintViolationException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(error -> {
            String fieldName = error.getPropertyPath().toString();
            String errorMessage = error.getMessage();
            fieldName=fieldName.equals("phoneNumbers[].<iterable element>")?"phoneNumbers":fieldName;
            errors.put(fieldName, errorMessage);
        });
        return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = { RecordNotFoundException.class})
    protected ResponseEntity<Object> handleRecordNotFoundException(RecordNotFoundException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        errors.put("Error", ex.getMessage());
        return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = { RuntimeException.class})
    protected ResponseEntity<Object> handleException(RuntimeException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        errors.put("Error", ex.getMessage());
        return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.SERVICE_UNAVAILABLE, request);
    }



}
