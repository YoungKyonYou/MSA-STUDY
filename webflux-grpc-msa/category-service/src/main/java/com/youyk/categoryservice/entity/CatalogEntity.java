package com.youyk.categoryservice.entity;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("catalog")
@Getter
public class CatalogEntity implements Serializable {
    @Generated
    @Id
    private Long id;

    private String productId;
    private String productName;
    private Integer stock;
    private Integer unitPrice;

    //현재 시간을 가져오게 함
    private LocalDate createdAt;

}
