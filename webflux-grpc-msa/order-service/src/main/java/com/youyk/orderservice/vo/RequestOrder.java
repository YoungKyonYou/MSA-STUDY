package com.youyk.orderservice.vo;

import lombok.Builder;

@Builder
public record RequestOrder(
        String productId,
        Integer qty,
        Integer unitPrice
) {

}
