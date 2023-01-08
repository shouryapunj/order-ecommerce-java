package com.order.ecommerce.entity;

import com.order.ecommerce.enums.OrderStatus;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@SuperBuilder
@Setter
@NoArgsConstructor
@Table(name = "ecommerce_order")
public class Order extends AbstractEntity {

    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(name = "order_status")
    private OrderStatus orderStatus = OrderStatus.PROCESSING;

    @Column(name = "customer_id")
    private String customerId;

    @Column(name = "sub_total", precision = 22, scale = 10)
    private BigDecimal subTotal;

    @Column(name = "total_amt", precision = 22, scale = 10)
    private BigDecimal totalAmt;

    @Column(name = "tax", precision = 22, scale = 10)
    private BigDecimal tax;

    @Column(name = "shipping_charges", precision = 22, scale = 10)
    private BigDecimal shippingCharges;

    @Column(name = "title")
    private String title;

    @Column(name = "shipping_mode")
    private String shippingMode;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id", name = "payment_id")
    private Payment payment;

    @OneToOne
    @JoinColumn(referencedColumnName = "id", name = "billing_address_id")
    private Address billingAddress;

    @OneToOne
    @JoinColumn(referencedColumnName = "id", name = "shipping_address_id")
    private Address shippingAddress;

    @OneToMany(targetEntity = OrderItem.class, fetch = FetchType.LAZY, mappedBy = "order")
    private List<OrderItem> orderItems;
}
