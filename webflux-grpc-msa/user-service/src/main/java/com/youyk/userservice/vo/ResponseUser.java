package com.youyk.userservice.vo;

import com.youyk.userservice.dto.UserDto;
import lombok.Builder;

import java.util.List;

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

    public static ResponseUser createdFrom(UserDto userDto) {
        return ResponseUser.builder()
                .email(userDto.email())
                .name(userDto.name())
                .userId(userDto.userId())
                .build();
    }
}
