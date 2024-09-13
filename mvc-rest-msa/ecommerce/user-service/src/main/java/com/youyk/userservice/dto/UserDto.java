package com.youyk.userservice.dto;

import com.youyk.userservice.jpa.entity.UserEntity;
import com.youyk.userservice.vo.RequestUser;
import java.util.Date;
import lombok.Builder;

@Builder
public record UserDto(
        String email,
        String name,
        String pwd,
        String userId,
        Date createdAt,

        String encryptedPwd
) {
    public static UserDto from(final RequestUser requestUser) {
        return UserDto.builder()
                .email(requestUser.email())
                .name(requestUser.name())
                .pwd(requestUser.pwd())
                .createdAt(new Date())
                .encryptedPwd("")
                .build();
    }

    public static UserDto from(final UserEntity user){
        return UserDto.builder()
                .email(user.getEmail())
                .name(user.getName())
                .userId(user.getUserId())
                .encryptedPwd(user.getEncryptedPwd())
                .build();
    }
}
