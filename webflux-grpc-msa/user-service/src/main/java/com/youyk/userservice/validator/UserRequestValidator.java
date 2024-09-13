package com.youyk.userservice.validator;

import com.youyk.userservice.dto.UserDto;
import com.youyk.userservice.vo.RequestUser;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import reactor.core.publisher.Mono;

public class UserRequestValidator {
    public static UnaryOperator<Mono<RequestUser>> validate(){
        //email의 사이즈는 2보다 커야함
        return mono -> mono.filter(strLenGreater(2, RequestUser::email))
                .switchIfEmpty(Mono.error(new IllegalArgumentException("email length should be greater than 2")))
                .filter(strLenGreater(2, RequestUser::name))
                .switchIfEmpty(Mono.error(new IllegalArgumentException("name length should be greater than 2")))
                .filter(strLenGreater(8, RequestUser::pwd))
                .switchIfEmpty(Mono.error(new IllegalArgumentException("password length should be greater than 8")));
    }

    private static Predicate<RequestUser> strLenGreater(int size, Function<RequestUser, String> function){
        return user -> function.apply(user).length() > size;
    }
}
