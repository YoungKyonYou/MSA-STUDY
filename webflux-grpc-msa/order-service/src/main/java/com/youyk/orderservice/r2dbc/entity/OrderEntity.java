package com.youyk.orderservice.r2dbc.entity;


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

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Table(name = "orders")
public class OrderEntity implements Serializable {
    @Generated
    @Id
    private Long id;

    private String productId;
    private Integer qty;
    private Integer unitPrice;
    private Integer totalPrice;
    private String userId;

    private String orderId;
    //현재 시간을 가져오게 함
    private LocalDate createdAt;


}
