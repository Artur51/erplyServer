package com.middleware.erply.controllers.errors;

import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ValidationExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolation(
            ConstraintViolationException ex,
            WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleBadCredentialsException(
            BadCredentialsException ex,
            WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleBadRequestException(
            ResponseStatusException ex) {
        final String message = ex.getMessage();
        return new ResponseEntity<>(message, ex.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        BindingResult bindingResult = ex.getBindingResult();
        List<String> errors = collectErrors(bindingResult);
        Map<String, Object> body = createErrorBody(status, errors);
        return new ResponseEntity<>(body, headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        String name = ex.getParameterName();
        List<String> errors = Collections.singletonList("Missing parameter " + name);
        Map<String, Object> body = createErrorBody(status, errors);
        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private Map<String, Object> createErrorBody(HttpStatus status, List<String> errors) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", status.value());
        body.put("errors", errors);
        return body;
    }

    public static List<String> collectErrors(
            BindingResult bindingResult) {
        return bindingResult.getFieldErrors().stream().map(MessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
    }
}
