package com.youyk.userservice.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Date;
import java.util.List;
import lombok.Builder;

@Builder
public record ResponseOrder(
        String productId,
        Integer qty,
        Integer unitPrice,
        Integer totalPrice,
        List<ResponseOrder> orders,
        Date createdAt
) {
}
