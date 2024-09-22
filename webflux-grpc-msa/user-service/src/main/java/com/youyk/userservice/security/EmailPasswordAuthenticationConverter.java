package com.youyk.userservice.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.youyk.userservice.vo.RequestLogin;
import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 여기서 실질적으로 아이디 패스워드를 어떻게 받아올지 정의할 수 있음
 * 나는 RequestLogin.java를 만들어서 email과 password를 받아오게끔 함
 * 처음 로그인하면 여기가 호출되고 바로 UserDetailsService로 넘어감
 */
@Component
public class EmailPasswordAuthenticationConverter implements ServerAuthenticationConverter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        return exchange.getRequest().getBody()
                .next()  // Reactive로 request body를 가져옴
                .flatMap(dataBuffer -> {
                    try {
                        // RequestLogin 객체로 변환
                        RequestLogin loginRequest = objectMapper.readValue(dataBuffer.asInputStream(), RequestLogin.class);
                        // email과 password로 Authentication Token 생성
                        Authentication auth = new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password());
                        return Mono.just(auth);
                    } catch (IOException e) {
                        return Mono.error(new RuntimeException("Invalid login request"));
                    }
                });
    }
}
