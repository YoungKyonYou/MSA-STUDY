package com.youyk.orderservice.service;


import com.youyk.orderservice.dto.OrderDto;
import com.youyk.orderservice.r2dbc.entity.OrderEntity;
import com.youyk.orderservice.r2dbc.repository.OrderRepository;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Override
    public Mono<OrderDto> createOrder(final Mono<OrderDto> orderDetails) {
        return orderDetails.flatMap(orderDto -> {
            final OrderEntity orderEntity = OrderEntity.builder()
                    .productId(orderDto.productId())
                    .qty(orderDto.qty())
                    .unitPrice(orderDto.unitPrice())
                    .totalPrice(orderDto.qty() * orderDto.unitPrice())
                    .orderId(UUID.randomUUID().toString())
                    .userId(orderDto.userId())
                    .createdAt(LocalDate.now())
                    .build();

            return orderRepository.save(orderEntity);
        }).map(OrderDto::from);

    }

    @Override
    public Mono<OrderDto> getOrderByOrderId(final String orderId) {
        return orderRepository.findByOrderId(orderId)
                .map(OrderDto::from);
    }

    @Override
    public Flux<OrderDto> getOrdersByUserId(final String userId) {
        return orderRepository.findByUserId(userId)
                .map(OrderDto::from);
    }
}
