package com.order.ecommerce.exception.handler;

import com.order.ecommerce.exception.OrderNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.constraints.NotNull;

@RestControllerAdvice
@Slf4j
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @NotNull
    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex,
        @NotNull HttpHeaders headers,
        HttpStatus status,
        @NotNull WebRequest request) {
        StringBuilder errors = new StringBuilder();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            errors.append(String.format("JSON payload validation failed for field %s, %s", fieldName, error.getDefaultMessage()));
        });
        return ResponseEntity.status(status).body(String.format("{\"error\" : \"%s\"}", String.format("JSON payload validation failed for field %s", errors)));
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<Object> handleOrderNotFoundException(OrderNotFoundException orderException, WebRequest webRequest) {
        return buildResponseEntity(orderException, HttpStatus.NOT_FOUND.value(), orderException.getMessage(), orderException.getMessage());
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleOtherException(Exception exception, WebRequest webRequest) {
        return buildResponseEntity(exception, HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), exception.getMessage());
    }

    private ResponseEntity<Object> buildResponseEntity(Exception exception, int status, String errorMessage, String detailedErrorMessage) {
        log.error(exception.getLocalizedMessage(), exception);
        return ResponseEntity.status(status).body(String.format("{\"error\" : \"%s\"}",errorMessage));
    }
}
