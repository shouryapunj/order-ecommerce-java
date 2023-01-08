package com.order.ecommerce.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@SuperBuilder
@Setter
@NoArgsConstructor
@Table(name = "ecommerce_product")
public class Product extends AbstractEntity {

    @Column(name = "sku", nullable = false)
    private String sku;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "price", nullable = false)
    private double price;
    @OneToMany(targetEntity = OrderItem.class, fetch = FetchType.LAZY, mappedBy = "product")
    private List<OrderItem> orderItems;
}
