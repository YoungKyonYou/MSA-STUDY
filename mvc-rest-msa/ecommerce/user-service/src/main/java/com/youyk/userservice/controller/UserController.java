package com.youyk.userservice.controller;

import com.youyk.userservice.dto.UserDto;
import com.youyk.userservice.service.UserService;
import com.youyk.userservice.vo.RequestUser;
import com.youyk.userservice.vo.ResponseUser;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.hc.core5.http.io.HttpFilterChain.ResponseTrigger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/")
public class UserController {
    private final UserService userService;
    private final Environment env;
    @Value("${greeting.message}")
    private String message;

    @GetMapping("/health_check")
    public String status() {
//        return "It's Working in User Service on PORT %s".formatted(env.getProperty("local.server.port"));
        return String.format("""
                        It's Working in User Service Webflux
                        ,port(local.server.port) %s
                        \n,port(server.port) %s
                        \n,token secret = %s
                        \n,token expiration time = %s
                        """,
                env.getProperty("local.server.port"),
                env.getProperty("server.port"),
                env.getProperty("token.secret"),
                env.getProperty("token.expiration_time"));
    }

    @GetMapping("/welcome")
    public String welcome(){
        return message;
    }

    @PostMapping("/users")
    public ResponseEntity<ResponseUser> createUser(@RequestBody @Valid RequestUser user){

        UserDto returnUserDto = userService.createUser(UserDto.from(user));

        ResponseUser responseUser = ResponseUser.createdFrom(returnUserDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }

    @GetMapping("/users")
    public ResponseEntity<List<ResponseUser>> getUsers(){
        List<ResponseUser> userResponse = ResponseUser.from(userService.getUserByAll());
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<ResponseUser> getUser(@PathVariable("userId") String userId){
        ResponseUser userResponse = ResponseUser.from(userService.getUserByUserId(userId));
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }
}
