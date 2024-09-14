package com.youyk.userservice.service;

import com.youyk.userservice.dto.UserDto;
import java.util.List;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<UserDto> createUser(Mono<UserDto> userDto);

    Flux<UserDto> getUserByAll();

    Mono<UserDto> getUserByUserId(String userId);
}
