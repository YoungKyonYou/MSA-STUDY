package com.youyk.userservice.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.youyk.userservice.dto.UserDto;
import java.util.List;
import lombok.Builder;

@JsonInclude(JsonInclude.Include.NON_NULL) // null 값은 제외하고 json으로 변환
@Builder
public record ResponseUser(
        String email,
        String name,
        String userId,
        List<ResponseOrder> orders
) {
    public static ResponseUser from(final UserDto userDto) {
        return ResponseUser.builder()
                .email(userDto.email())
                .name(userDto.name())
                .userId(userDto.userId())
                .build();
    }
    public static List<ResponseUser> from(final List<UserDto> userDtos){
        return userDtos.stream().map(ResponseUser::from).toList();
    }
}
