package com.order.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class OrderECommerceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderECommerceApplication.class, args);
    }

}