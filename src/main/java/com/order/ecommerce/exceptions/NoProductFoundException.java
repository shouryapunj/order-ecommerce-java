package com.order.ecommerce.exceptions;

public class NoProductFoundException extends RuntimeException {
    public NoProductFoundException(String errorMessage) {
        super(errorMessage);
    }
}
