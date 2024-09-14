package com.youyk.userservice.service;

import com.youyk.userservice.dto.UserDto;
import com.youyk.userservice.jpa.entity.UserEntity;
import com.youyk.userservice.jpa.repository.UserRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.StreamSupport;
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

    @Override
    public UserDto getUserByUserId(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다. userId=" + userId));

        return UserDto.from(userEntity);
    }

    @Override
    public List<UserDto> getUserByAll() {
        return userRepository.findAll().stream().map(UserDto::from).toList();
    }
}
