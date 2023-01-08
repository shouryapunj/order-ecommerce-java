package com.order.ecommerce.service;

import com.order.ecommerce.dto.OrderDto;
import com.order.ecommerce.dto.OrderItemDto;
import com.order.ecommerce.dto.OrderResponseDto;
import com.order.ecommerce.dto.AddressDto;
import com.order.ecommerce.dto.ProductDto;
import com.order.ecommerce.entity.*;
import com.order.ecommerce.enums.OrderStatus;
import com.order.ecommerce.exception.OrderNotFoundException;
import com.order.ecommerce.mapper.OrderDetailsMapper;
import com.order.ecommerce.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final IOrderRepository orderRepository;
    private final IOrderItemRepository orderItemRepository;
    private final IPaymentRepository paymentRepository;
    private final IAddressRepository addressRepository;

    private final IProductService productService;
    private final OrderDetailsMapper orderDetailsMapper = Mappers.getMapper(OrderDetailsMapper.class);

    @Override
    @Transactional
    public OrderResponseDto createOrder(OrderDto orderDto) {
        log.info("Creating Order for customer = {}", orderDto.getCustomerId());

        log.info("Verifying all products exists before generating order");
        List<Long> productIds = orderDto.getOrderItems().stream().map(OrderItemDto::getProductId).distinct().collect(Collectors.toList());
        List<ProductDto> products = productService.findAllById(productIds);
        if (products == null || products.isEmpty() || products.size() != productIds.size()) {
            log.info("Not all product(s) exist, failed to create order!");
            return null;
        }

        Order order = generateOrder(orderDto);
        log.info("Generated order for orderId = {}", order.getId());

        Order savedOrder = orderRepository.save(order);
        Long savedOrderId = savedOrder.getId();
        List<OrderItem> orderItemList = buildOrderItems(orderDto.getOrderItems(), savedOrderId);
        orderItemRepository.saveAll(orderItemList);

        log.info("Successfully saved order & order items with id = {} for customer = {} on {}", savedOrder.getId(),  savedOrder.getCustomerId(), savedOrder.getCreatedAt());

        return OrderResponseDto.builder()
                .id(savedOrderId)
                .orderStatus(savedOrder.getOrderStatus())
                .build();
    }

    @Override
    public OrderDto findOrderById(Long orderId) {
        log.info("Finding order for orderId = {}", orderId);
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isEmpty()) {
            log.info("Cannot find order with id = {}", orderId);
            throw new OrderNotFoundException(String.format("Order with id=%d does not exist",orderId));
        }

        log.info("Successfully found order for orderId = {}", orderId);
        return orderDetailsMapper.toOrderDto(order.get());
    }

    @Override
    public void updateOrderStatus(Long orderId, String status) {
        OrderDto orderDto = findOrderById(orderId);

        if (orderDto == null) {
            log.info("Cannot update status for orderId = {}", orderId);
            return;
        }

        List<OrderStatus> orderStatusList = Arrays.stream(OrderStatus.values()).filter(orderStatus -> orderStatus.toString().equalsIgnoreCase(status)).toList();
        if (orderStatusList.isEmpty()) {
            log.error("Invalid status = {}, failed to update order status for id = {}", status, orderId);
            return;
        }

        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if(optionalOrder.isPresent()) {
            Order orderToBeUpdated = optionalOrder.get();
            orderToBeUpdated.setOrderStatus(OrderStatus.valueOf(status));
            orderRepository.save(optionalOrder.get());
            log.info("Successfully updated order status to = {} for order id = {}", status.toUpperCase(), orderId);
        } else{
            log.error("Invalid Order Id={}", orderId);
            throw new OrderNotFoundException(String.format("Order with Id=%d does not exist", orderId));
        }

    }

    private Order generateOrder(OrderDto orderDto) {
        Order order = orderDetailsMapper.toOrderEntity(orderDto);
        Payment payment = buildAndSavePayment(orderDto.getAmount(), orderDto.getPaymentMode());
        order.setPayment(payment);

        Address billingAddress = buildAndLoadAddress(orderDto.getBillingAddress());
        Address shippingAddress = buildAndLoadAddress(orderDto.getShippingAddress());
        order.setBillingAddress(billingAddress);
        order.setShippingAddress(shippingAddress);
        return order;
    }

    private List<OrderItem> buildOrderItems(List<OrderItemDto> orderItemsDtoList, long orderId) {
        List<OrderItem> orderItemList = orderItemsDtoList
                .stream()
                .map(orderItemDto -> new OrderItem(new OrderItemPk(orderItemDto.getProductId(), orderId), null, null, orderItemDto.getQuantity()))
                .collect(Collectors.toList());
        log.info("Saving order item list for order id = {}", orderId);
        return (List<OrderItem>) orderItemRepository.saveAll(orderItemList);
    }

    private Payment buildAndSavePayment(BigDecimal amount, String paymentMode) {
        Payment payment = Payment.builder()
                .amount(amount)
                .paymentMode(paymentMode)
                .order(null)
        .build();
        log.info("Saving payment details for payment id = {}", payment.getId());
        return paymentRepository.save(payment);
    }

    private Address buildAndLoadAddress(AddressDto addressDto) {
        Address addressEntity = orderDetailsMapper.toAddressEntity(addressDto);
        log.info("Saving billing/shipping address for address id = {}", addressEntity.getId());
        return addressRepository.save(addressEntity);
    }
}
