package com.youyk.userservice.dto;

import com.youyk.userservice.jpa.entity.UserEntity;
import com.youyk.userservice.vo.RequestUser;
import com.youyk.userservice.vo.ResponseOrder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.Builder;

@Builder
public record UserDto(
        String email,
        String name,
        String pwd,
        String userId,
        Date createdAt,

        String encryptedPwd,
        List<ResponseOrder> orders
) {
    public static UserDto from(final RequestUser requestUser) {
        return UserDto.builder()
                .email(requestUser.email())
                .name(requestUser.name())
                .pwd(requestUser.pwd())
                .createdAt(new Date())
                .encryptedPwd("")
                .orders(new ArrayList<>())
                .build();
    }

    public static UserDto from(final UserEntity user){
        return UserDto.builder()
                .email(user.getEmail())
                .name(user.getName())
                .userId(user.getUserId())
                .encryptedPwd(user.getEncryptedPwd())
                .createdAt(new Date())
                .orders(new ArrayList<>())
                .build();
    }
}
