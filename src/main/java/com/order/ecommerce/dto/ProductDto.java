package com.order.ecommerce.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
public class ProductDto {

    private Long id;

    @NotNull
    private final String sku;

    @NotNull
    private final String title;

    @NotNull
    private final String description;

    private final BigDecimal price;
}
