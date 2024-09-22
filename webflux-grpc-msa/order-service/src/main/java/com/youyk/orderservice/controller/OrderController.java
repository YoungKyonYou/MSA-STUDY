package com.youyk.orderservice.controller;

import com.youyk.orderservice.dto.OrderDto;
import com.youyk.orderservice.service.OrderService;
import com.youyk.orderservice.vo.RequestOrder;
import com.youyk.orderservice.vo.ResponseOrder;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RequestMapping("/")
@RestController
public class OrderController {
    private final Environment env;
    private final OrderService orderService;

    @GetMapping("/health_check")
    public String status() {
        return "It's Working in Order Service on PORT %s".formatted(env.getProperty("local.server.port"));
    }

    @PostMapping("/{userId}/orders")
    public Mono<ResponseEntity<ResponseOrder>> createOrder(@PathVariable("userId") String userId, @RequestBody Mono<RequestOrder> order){
        return order.map(dto ->
                OrderDto.of(dto, userId))
                .flatMap(dto -> orderService.createOrder(Mono.just(dto))
                .map(o -> {
                    ResponseOrder from = ResponseOrder.from(o);
                    return ResponseEntity.status(HttpStatus.CREATED).body(from);
                }));
    }

    @GetMapping("/{userId}/orders")
    public Flux<ResponseOrder> getOrder(@PathVariable("userId") String userId){
        return orderService.getOrdersByUserId(userId)
                .map(ResponseOrder::from);
    }


}
