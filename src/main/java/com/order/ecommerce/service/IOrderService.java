package com.order.ecommerce.service;

import com.order.ecommerce.dto.OrderResponseDto;
import com.order.ecommerce.dto.OrderDto;

public interface IOrderService {
    OrderResponseDto createOrder(OrderDto orderDto);

    OrderDto findOrderById(Long id);

    void updateOrderStatus(Long id, String status);
}
