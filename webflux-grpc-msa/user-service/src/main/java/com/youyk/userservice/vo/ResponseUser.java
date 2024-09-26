package com.youyk.userservice.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.youyk.userservice.dto.UserDto;
import lombok.Builder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public record ResponseUser(
        String email,
        String name,
        String userId,
        List<ResponseOrder> orders
) {
    public static ResponseUser from(UserDto userDto) {
        return ResponseUser.builder()
                .email(userDto.email())
                .name(userDto.name())
                .userId(userDto.userId())
                .orders(userDto.orders())
                .build();
    }

    public static ResponseUser of(UserDto userDto, List<com.youyk.msa.ResponseOrder> orders) {

        return ResponseUser.builder()
                .email(userDto.email())
                .name(userDto.name())
                .userId(userDto.userId())
                .orders(ResponseOrder.from(orders))
                .build();

    }

    public static Mono<ResponseUser> of(UserDto userDto, Flux<com.youyk.msa.ResponseOrder> orders) {

        return orders.collectList().map(ResponseOrder::from).map(ordersList -> ResponseUser.builder()
                .email(userDto.email())
                .name(userDto.name())
                .userId(userDto.userId())
                .orders(ordersList)
                .build());


    /*    return ResponseUser.builder()
                .email(userDto.email())
                .name(userDto.name())
                .userId(userDto.userId())
                .orders(ResponseOrder.from(orders))
                .build();*/
    }

    public static ResponseUser createdFrom(UserDto userDto) {
        return ResponseUser.builder()
                .email(userDto.email())
                .name(userDto.name())
                .userId(userDto.userId())
                .build();
    }

}
