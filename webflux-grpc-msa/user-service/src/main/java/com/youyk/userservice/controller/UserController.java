package com.youyk.userservice.controller;

import com.youyk.userservice.dto.UserDto;
import com.youyk.userservice.service.UserService;
import com.youyk.userservice.vo.RequestUser;
import com.youyk.userservice.vo.ResponseUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/")
public class UserController {
    private final UserService userService;
    private final Environment env;
    @Value("${greeting.message}")
    private String message;

    @GetMapping("/health_check")
    public Mono<String> status() {
        return Mono.fromSupplier(() -> String.format("""
                        It's Working in User Service Webflux
                        ,port(local.server.port) %s
                        \n,port(server.port) %s
                        \n,token secret = %s
                        \n,token expiration time = %s
                        """,
                env.getProperty("local.server.port"),
                env.getProperty("server.port"),
                env.getProperty("token.secret"),
                env.getProperty("token.expiration_time")));
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
