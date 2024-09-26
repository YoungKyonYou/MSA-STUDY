package com.youyk.userservice.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import lombok.Builder;
import reactor.core.publisher.Flux;

@Builder
public record ResponseOrder(
        String productId,
        Integer qty,
        Integer unitPrice,
        Integer totalPrice,
        LocalDate createdAt
) {

    public static List<ResponseOrder> from(List<com.youyk.msa.ResponseOrder> orders) {

        return orders.stream()
                .map(order -> {
                    LocalDate localDate = LocalDate.of(order.getCreatedAt().getYear(), order.getCreatedAt().getMonth(), order.getCreatedAt().getDay());
                    return ResponseOrder.builder()
                            .productId(order.getProductId())
                            .qty(order.getQty())
                            .unitPrice(order.getUnitPrice())
                            .totalPrice(order.getTotalPrice())
                            .createdAt(localDate)
                            .build();
                })
                .toList();
    }
}
