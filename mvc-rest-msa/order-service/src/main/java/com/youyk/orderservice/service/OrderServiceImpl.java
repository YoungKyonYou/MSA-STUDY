package com.youyk.orderservice.service;


import com.youyk.orderservice.dto.OrderDto;
import com.youyk.orderservice.jpa.entity.OrderEntity;
import com.youyk.orderservice.jpa.repository.OrderRepository;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Override
    public OrderDto createOrder(final OrderDto orderDetails) {
        final OrderEntity orderEntity = OrderEntity.builder()
                .productId(orderDetails.productId())
                .qty(orderDetails.qty())
                .unitPrice(orderDetails.unitPrice())
                .totalPrice(orderDetails.qty() * orderDetails.unitPrice())
                .orderId(UUID.randomUUID().toString())
                .userId(orderDetails.userId())
                .createdAt(Date.from(Instant.now()))
                .build();

        final OrderEntity save = orderRepository.save(orderEntity);

        return OrderDto.from(save);
    }

    @Override
    public OrderDto getOrderByOrderId(final String orderId) {
        return orderRepository.findByOrderId(orderId)
                .map(OrderDto::from)
                .orElseThrow(() -> new IllegalArgumentException("해당 주문이 존재하지 않습니다."));
    }

    @Override
    public List<OrderDto> getOrdersByUserId(final String userId) {
        return orderRepository.findByUserId(userId)
                .stream()
                .map(OrderDto::from)
                .toList();
    }
}
