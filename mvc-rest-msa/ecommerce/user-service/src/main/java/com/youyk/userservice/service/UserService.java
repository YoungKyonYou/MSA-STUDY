package com.youyk.userservice.service;

import com.youyk.userservice.dto.UserDto;
import com.youyk.userservice.jpa.entity.UserEntity;
import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto);
    UserDto getUserByUserId(String userId);
    List<UserDto> getUserByAll();
}
