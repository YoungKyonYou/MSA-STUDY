package com.youyk.userservice.service;

import com.youyk.userservice.dto.UserDto;
import com.youyk.userservice.jpa.entity.UserEntity;
import com.youyk.userservice.jpa.repository.UserRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.StreamSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    @Override
    public UserDto getUserDetailsByEmail(String userName) {
        final UserEntity userEntity = userRepository.findByEmail(userName)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다. email=" + userName));

        return UserDto.from(userEntity);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final UserEntity userEntity = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다. email=" + username));

        /**
         * userEntity.getEmail(): 사용자의 이메일 주소입니다. 이것은 사용자의 고유 식별자로 사용됩니다.
         * userEntity.getEncryptedPwd(): 사용자의 암호화된 비밀번호입니다.
         * true: 계정이 활성화되어 있는지를 나타내는 불리언 값입니다. true는 계정이 활성화되어 있음을 나타냅니다.
         * true: 계정이 만료되지 않았음을 나타내는 불리언 값입니다. true는 계정이 만료되지 않았음을 나타냅니다.
         * true: 계정의 자격 증명(비밀번호)이 만료되지 않았음을 나타내는 불리언 값입니다. true는 자격 증명이 만료되지 않았음을 나타냅니다.
         * true: 계정이 잠기지 않았음을 나타내는 불리언 값입니다. true는 계정이 잠기지 않았음을 나타냅니다.
         * List.of(): 사용자에게 부여된 권한의 목록입니다. 이 경우, 사용자에게는 아무런 권한이 부여되지 않았습니다.
         */
        return new User(userEntity.getEmail(), userEntity.getEncryptedPwd(),true,true, true, true, List.of());
    }
}
