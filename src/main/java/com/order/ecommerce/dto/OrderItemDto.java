package com.order.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
public class OrderItemDto {

    @NotNull
    private final Long productId;
    @NotNull
    private final String quantity;
}
