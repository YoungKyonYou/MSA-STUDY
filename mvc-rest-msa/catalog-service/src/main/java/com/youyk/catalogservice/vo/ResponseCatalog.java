package com.youyk.catalogservice.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.youyk.catalogservice.dto.CatalogDto;
import com.youyk.catalogservice.entity.CatalogEntity;
import java.util.Date;
import java.util.List;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ResponseCatalog(
        String productId,
        String productName,
        Integer stock,
        Integer unitPrice,
        Date createdAt
) {
    public static List<ResponseCatalog> from(final List<CatalogEntity> entity){
        return entity.stream()
                .map(d -> ResponseCatalog.builder()
                        .productId(d.getProductId())
                        .stock(d.getStock())
                        .productName(d.getProductName())
                        .unitPrice(d.getUnitPrice())
                        .createdAt(new Date())
                        .build())
                .toList();
    }
}
