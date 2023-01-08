package com.order.ecommerce.entity;

import com.order.ecommerce.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@SuperBuilder
@Setter
@NoArgsConstructor
@Table(name = "ecommerce_payment")
public class Payment extends AbstractEntity{

    @Column(name = "amount", precision = 22, scale = 10, nullable = false)
    private BigDecimal amount;

    @Column(name = "payment_mode", nullable = false)
    private String paymentMode;

    @Builder.Default
    @Column(name = "confirmation_number", nullable = false)
    private String confirmationNumber = UUID.randomUUID().toString();

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    private PaymentStatus paymentStatus = PaymentStatus.PROCESSING;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "payment")
    private Order order;
}
