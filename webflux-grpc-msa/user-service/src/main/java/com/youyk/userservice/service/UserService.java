package com.youyk.userservice.service;

import com.youyk.userservice.dto.UserDto;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<UserDto> createUser(Mono<UserDto> userDto);

}
