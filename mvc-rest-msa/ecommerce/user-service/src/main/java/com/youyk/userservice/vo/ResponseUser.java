package com.youyk.userservice.vo;

import com.youyk.userservice.dto.UserDto;
import lombok.Builder;

@Builder
public record ResponseUser(
        String email,
        String name,
        String userId
) {
    public static ResponseUser from(UserDto userDto) {
        return ResponseUser.builder()
                .email(userDto.email())
                .name(userDto.name())
                .userId(userDto.userId())
                .build();
    }
}
