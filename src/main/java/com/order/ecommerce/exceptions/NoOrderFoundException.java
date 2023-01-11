package com.order.ecommerce.exceptions;

public class NoOrderFoundException extends RuntimeException {
    public NoOrderFoundException(String errorMessage) {
        super(errorMessage);
    }
}
