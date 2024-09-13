package com.youyk.userservice.service;

import com.youyk.userservice.dto.UserDto;
import com.youyk.userservice.jpa.entity.UserEntity;
import com.youyk.userservice.jpa.repository.UserRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    @Override
    public UserDto createUser(UserDto userDto) {
        UserEntity user = UserEntity.from(userDto, passwordEncoder);

        UserEntity saveUser = userRepository.save(user);

        return UserDto.from(saveUser);
    }
}
