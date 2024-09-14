package com.youyk.userservice.controller;

import com.youyk.userservice.dto.UserDto;
import com.youyk.userservice.service.UserService;
import com.youyk.userservice.vo.RequestUser;
import com.youyk.userservice.vo.ResponseUser;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
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
        return Mono.just("It's Working in User Service Webflux");
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

    @GetMapping("/users")
    public Flux<ResponseUser> getUsers(){
        return userService.getUserByAll()
                .map(ResponseUser::from);


    }

    @GetMapping("/users/{userId}")
    public Mono<ResponseEntity<ResponseUser>> getUser(@PathVariable("userId") String userId){
        return userService.getUserByUserId(userId)
                .map(ResponseUser::from)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
