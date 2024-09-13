package com.youyk.userservice.r2dbc.entity;

import com.youyk.userservice.dto.UserDto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("users")
@Getter
public class UserEntity {
    @Generated
    @Id
    private Long id;

    private String email;
    private String name;
    private String userId;
    private String encryptedPwd;

    public static UserEntity from(final UserDto userDto, final BCryptPasswordEncoder passwordEncoder){
        return UserEntity.builder()
                .email(userDto.email())
                .userId(UUID.randomUUID().toString())
                .name(userDto.name())
                .encryptedPwd(passwordEncoder.encode(userDto.pwd()))
                .build();
    }
}
