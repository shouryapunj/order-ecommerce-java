package com.order.ecommerce.controller;

import com.order.ecommerce.dto.OrderResponseDto;
import com.order.ecommerce.dto.OrderDto;
import com.order.ecommerce.service.IOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final IOrderService orderService;

    /**
     * Creates order
     *
     * @param orderDto
     * @return
     */
    @PostMapping
    @Operation(summary = "Create an order", description = "Create an order")
    public OrderResponseDto createOrder(@RequestBody OrderDto orderDto) {
        validateArgument(orderDto);
        return orderService.createOrder(orderDto);
    }

    /**
     * Finds Order by Id
     *
     * @param orderId
     * @return
     */
    @GetMapping("/{orderId}")
    @Operation(summary = "Find order", description = "Find order by id")
    public ResponseEntity<OrderDto> findOrderBy(@Parameter(in = ParameterIn.PATH, description = "order id", required=true, schema=@Schema()) @PathVariable(name = "orderId") Long orderId) {
        OrderDto orderDto = orderService.findOrderById(orderId);
        return orderDto == null ? ResponseEntity.notFound().build()  : ResponseEntity.ok(orderService.findOrderById(orderId));
    }

    /**
     * Updates order status
     *
     * @param orderId
     * @param orderStatus
     */
    @PatchMapping("/{orderId}")
    @Operation(summary = "Update order status", description = "Update order status")
    public void updateOrderStatus(@PathVariable("orderId") Long orderId,
                                  @RequestParam(name = "orderStatus") String orderStatus) {
        validateArgument(orderId == null || orderId <= 0, "order id cannot be null or empty");
        validateArgument(orderStatus == null || orderStatus.isEmpty(), "order status cannot be null or empty");
        orderService.updateOrderStatus(orderId, orderStatus);
    }

    private void validateArgument(OrderDto orderDto) {
        validateArgument(orderDto.getCustomerId() == null || orderDto.getCustomerId().isEmpty(), "customer id cannot be null or empty");
        validateArgument(orderDto.getTitle() == null || orderDto.getTitle().isEmpty(), "title cannot be null or empty");
        validateArgument(orderDto.getPaymentMode() == null || orderDto.getPaymentMode().isEmpty(), "payment mode cannot be null or empty");
        validateArgument(orderDto.getBillingAddress() == null, "billing address cannot be null");
        validateArgument(orderDto.getOrderItems() == null || orderDto.getOrderItems().isEmpty(), "order items cannot be null or empty");
        validateArgument(orderDto.getOrderStatus() == null || orderDto.getOrderStatus().isEmpty(), "order status cannot be null or empty");
    }

    private void validateArgument(boolean condition, String message) {
        if (condition) {
            log.error("Error while processing request with message = {}", message);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
        }
    }

}
