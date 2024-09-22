package com.youyk.orderservice.dto;

import com.youyk.orderservice.r2dbc.entity.OrderEntity;
import com.youyk.orderservice.vo.RequestOrder;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import lombok.Builder;

@Builder
public record OrderDto(
        String productId,
        Integer qty,
        Integer unitPrice,
        Integer totalPrice,

        String orderId,
        String userId

) implements Serializable {

    public static List<OrderDto> from(final List<OrderEntity> entities) {
        return entities.stream()
                .map(entity -> OrderDto.builder()
                        .productId(entity.getProductId())
                        .qty(entity.getQty())
                        .unitPrice(entity.getUnitPrice())
                        .totalPrice(entity.getTotalPrice())
                        .orderId(entity.getOrderId())
                        .userId(entity.getUserId())
                        .build())
                .toList();
    }

    public static OrderDto from(final OrderEntity entity){
        return OrderDto.builder()
                .productId(entity.getProductId())
                .qty(entity.getQty())
                .unitPrice(entity.getUnitPrice())
                .totalPrice(entity.getTotalPrice())
                .orderId(entity.getOrderId())
                .userId(entity.getUserId())
                .build();
    }

    public static OrderDto of(final RequestOrder requestOrder, final String userId){
        return OrderDto.builder()
                .productId(requestOrder.productId())
                .qty(requestOrder.qty())
                .unitPrice(requestOrder.unitPrice())
                .totalPrice(requestOrder.qty() * requestOrder.unitPrice())
                .orderId(UUID.randomUUID().toString())
                .userId(userId)
                .build();
    }

}
