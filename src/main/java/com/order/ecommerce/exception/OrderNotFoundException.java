package com.order.ecommerce.exception;

public class OrderNotFoundException extends RuntimeException{

    public OrderNotFoundException(String message){
        super(message);
    }

    public OrderNotFoundException(String message, Throwable error){
        super(message,error);
    }
}
