package com.youyk.orderservice.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.youyk.orderservice.dto.OrderDto;
import com.youyk.orderservice.r2dbc.entity.OrderEntity;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import lombok.Builder;
import org.springframework.cglib.core.Local;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ResponseOrder(
        String productId,
        Integer qty,
        Integer totalPrice,
        Integer unitPrice,
        LocalDate createdAt,

        String orderId
) {
    public static List<ResponseOrder> from(final List<OrderEntity> entity){
        return entity.stream()
                .map(d -> ResponseOrder.builder()
                        .productId(d.getProductId())
                        .qty(d.getQty())
                        .totalPrice(d.getTotalPrice())
                        .orderId(d.getOrderId())
                        .unitPrice(d.getUnitPrice())
                        .createdAt(LocalDate.now())
                        .build())
                .toList();
    }

    public static ResponseOrder from(final OrderDto dto){
        return ResponseOrder.builder()
                .productId(dto.productId())
                .qty(dto.qty())
                .totalPrice(dto.totalPrice())
                .orderId(dto.orderId())
                .unitPrice(dto.unitPrice())
                .createdAt(LocalDate.now())
                .build();
    }

    public static List<ResponseOrder> fromDto(final List<OrderDto> dtos){
        return dtos.stream()
                .map(d -> ResponseOrder.builder()
                        .productId(d.productId())
                        .qty(d.qty())
                        .totalPrice(d.totalPrice())
                        .orderId(d.orderId())
                        .unitPrice(d.unitPrice())
                        .createdAt(LocalDate.now())
                        .build())
                .toList();
    }
}
