package com.youyk.orderservice.service;


import com.youyk.orderservice.dto.OrderDto;
import java.util.List;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderService {
    Mono<OrderDto> createOrder(final Mono<OrderDto> orderDetails);
    Mono<OrderDto> getOrderByOrderId(final String orderId);
    Flux<OrderDto> getOrdersByUserId(final String userId);
}
