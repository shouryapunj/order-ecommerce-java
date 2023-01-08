package com.order.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.order.ecommerce.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
public class OrderResponseDto {

    @NotNull
    private final Long id;

    @NotNull
    private final OrderStatus orderStatus;

}
