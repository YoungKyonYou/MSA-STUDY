package com.youyk.categoryservice.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.youyk.categoryservice.entity.CatalogEntity;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import lombok.Builder;
import reactor.core.publisher.Flux;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ResponseCatalog(
        String productId,
        String productName,
        Integer stock,
        Integer unitPrice,
        //webflux에서는 Date를 사용하지 않음 (convert가 안됨)
        LocalDate createdAt
) {
    public static ResponseCatalog from(final CatalogEntity entity){
            return ResponseCatalog.builder()
                    .productId(entity.getProductId())
                    .productName(entity.getProductName())
                    .stock(entity.getStock())
                    .unitPrice(entity.getUnitPrice())
                    .createdAt(entity.getCreatedAt())
                    .build();
    }
}
