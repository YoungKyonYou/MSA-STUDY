package com.youyk.userservice.vo;

import java.util.Date;
import lombok.Builder;

@Builder
public record ResponseOrder(
        String productId,
        Integer qty,
        Integer unitPrice,
        Integer totalPrice,
        Date createdAt
) {
}
