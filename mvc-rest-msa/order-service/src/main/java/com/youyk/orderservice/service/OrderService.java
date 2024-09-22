package com.youyk.orderservice.service;


import com.youyk.orderservice.dto.OrderDto;
import com.youyk.orderservice.jpa.entity.OrderEntity;
import java.util.List;

public interface OrderService {
    OrderDto createOrder(final OrderDto orderDetails);
    OrderDto getOrderByOrderId(final String orderId);
    List<OrderDto> getOrdersByUserId(final String userId);
}
