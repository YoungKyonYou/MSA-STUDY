package com.youyk.userservice.controller;

import com.youyk.userservice.dto.UserDto;
import com.youyk.userservice.service.UserService;
import com.youyk.userservice.vo.RequestUser;
import com.youyk.userservice.vo.ResponseUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/")
public class UserController {
    private final UserService userService;
    @Value("${greeting.message}")
    private String message;

    @GetMapping("/health_check")
    public Mono<String> status() {
        return Mono.just("It's Working in User Service");
    }

    @GetMapping("/welcome")
    public Mono<String> welcome(){
        return Mono.just(message);
    }

    @PostMapping("/users")
    public Mono<ResponseUser> createUser(@RequestBody @Valid Mono<RequestUser> userReqMono){

        return userReqMono
                .map(UserDto::from)
                .flatMap(userDto -> this.userService.createUser(Mono.just(userDto)))
                .map(ResponseUser::from);
    }
}
