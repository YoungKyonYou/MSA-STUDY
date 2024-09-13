package com.youyk.userservice.service;

import com.youyk.userservice.dto.UserDto;

import com.youyk.userservice.r2dbc.entity.UserEntity;
import com.youyk.userservice.r2dbc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    @Override
    public Mono<UserDto> createUser(Mono<UserDto> userDto) {
        return userDto.map(dto -> UserEntity.from(dto, passwordEncoder))
                //여기서 save의 파라미터 타입은 UserEntity이다. Mono가 아니다. flatMap은
                //또 반환 타입을 Mono로 바꿔준다.
                .flatMap(this.userRepository::save)
                .map(UserDto::from);
    }
}
