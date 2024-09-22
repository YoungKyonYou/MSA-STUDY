package com.youyk.orderservice.vo;

import com.youyk.orderservice.dto.OrderDto;
import lombok.Builder;

@Builder
public record RequestOrder(
        String productId,
        Integer qty,
        Integer unitPrice
) {

}
