package com.youyk.userservice.controller;

import com.youyk.userservice.dto.UserDto;
import com.youyk.userservice.service.UserService;
import com.youyk.userservice.vo.RequestUser;
import com.youyk.userservice.vo.ResponseUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/")
public class UserController {
    private final UserService userService;
    @Value("${greeting.message}")
    private String message;

    @GetMapping("/health_check")
    public String status() {
        return "It's Working in User Service";
    }

    @GetMapping("/welcome")
    public String welcome(){
        return message;
    }

    @PostMapping("/users")
    public ResponseEntity<ResponseUser> createUser(@RequestBody @Valid RequestUser user){

        UserDto returnUserDto = userService.createUser(UserDto.from(user));

        ResponseUser responseUser = ResponseUser.from(returnUserDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }
}
