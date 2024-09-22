package com.youyk.orderservice.controller;

import com.youyk.orderservice.dto.OrderDto;
import com.youyk.orderservice.jpa.entity.OrderEntity;
import com.youyk.orderservice.service.OrderService;
import com.youyk.orderservice.vo.RequestOrder;
import com.youyk.orderservice.vo.ResponseOrder;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<ResponseOrder> createOrder(@PathVariable("userId") String userId, @RequestBody RequestOrder order){

        final OrderDto orderDto = orderService.createOrder(OrderDto.of(order, userId));

        final ResponseOrder response = ResponseOrder.from(orderDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<ResponseOrder>> getOrder(@PathVariable("userId") String userId){
        final List<ResponseOrder> response = ResponseOrder.fromDto(orderService.getOrdersByUserId(userId));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


}
