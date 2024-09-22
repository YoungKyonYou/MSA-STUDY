package com.youyk.userservice.service;

import com.youyk.userservice.dto.UserDto;
import com.youyk.userservice.jpa.entity.UserEntity;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto userDto);
    UserDto getUserByUserId(String userId);
    List<UserDto> getUserByAll();

    UserDto getUserDetailsByEmail(String userName);
}
