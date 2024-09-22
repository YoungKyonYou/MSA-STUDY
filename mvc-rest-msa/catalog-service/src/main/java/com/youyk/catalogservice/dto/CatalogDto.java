package com.youyk.catalogservice.dto;

import com.youyk.catalogservice.entity.CatalogEntity;
import java.io.Serializable;
import java.util.List;
import lombok.Builder;

@Builder
public record CatalogDto(
        String productId,
        Integer qty,
        Integer unitPrice,
        Integer totalPrice,

        String orderId,
        String userId

) implements Serializable {

    public static List<CatalogDto> from(final List<CatalogEntity> entities){
        return entities.stream()
                .map(entity -> CatalogDto.builder()
                        .productId(entity.getProductId())
                        .qty(entity.getStock())
                        .unitPrice(entity.getUnitPrice())
                        .totalPrice(entity.getStock() * entity.getUnitPrice())
                        .build())
                .toList();
    }
}
