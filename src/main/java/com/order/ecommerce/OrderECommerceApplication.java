package com.order.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.order.ecommerce")
public class OrderECommerceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderECommerceApplication.class, args);
    }

}