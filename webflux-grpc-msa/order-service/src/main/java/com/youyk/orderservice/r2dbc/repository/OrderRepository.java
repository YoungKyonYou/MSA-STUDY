package com.youyk.orderservice.r2dbc.repository;

import com.youyk.orderservice.r2dbc.entity.OrderEntity;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface OrderRepository extends ReactiveCrudRepository<OrderEntity, Long> {
    Mono<OrderEntity> findByOrderId(String orderId);
    Flux<OrderEntity> findByUserId(String userId);

}
