package com.youyk.userservice.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter extends AuthenticationWebFilter {
    private Environment env;

    public JwtAuthenticationFilter(ReactiveAuthenticationManager authenticationManager, Environment env, EmailPasswordAuthenticationConverter emailPasswordAuthenticationConverter) {
        super(authenticationManager);

        this.env = env;


        setRequiresAuthenticationMatcher(exchange -> {
            String path = exchange.getRequest().getPath().value();
            if("/login".equals(path)){
                return ServerWebExchangeMatcher.MatchResult.match();
            }
            return ServerWebExchangeMatcher.MatchResult.notMatch();
        });

        setServerAuthenticationConverter(emailPasswordAuthenticationConverter);


        setAuthenticationSuccessHandler(new JwtAuthenticationSuccessHandler());
    }




    // JWT 성공 핸들러
    private class JwtAuthenticationSuccessHandler implements ServerAuthenticationSuccessHandler {

        @Override
        public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
            // JWT 토큰 생성
            String token = generateJwtToken(authentication.getName());

            // 응답 헤더에 JWT 토큰 추가
            webFilterExchange.getExchange().getResponse().getHeaders()
                    .add("Authorization", "Bearer " + token);

            webFilterExchange.getExchange().getResponse().getHeaders()
                    .add("UserId", authentication.getName());
            return Mono.empty();
        }
    }

    private String generateJwtToken(String userId) {
        return Jwts.builder()
                .subject(userId)
                .expiration(new Date(System.currentTimeMillis() + Long.parseLong(
                        Objects.requireNonNull(env.getProperty("token.expiration_time")))))
                .signWith(getSigningKey(), SIG.HS512)
                .compact();
    }
    private SecretKey getSigningKey() {
        byte[] keyBytes = Base64.getDecoder().decode(env.getProperty("token.secret"));
        return new SecretKeySpec(keyBytes, 0, keyBytes.length, "HmacSHA512");
    }



    @Override
    protected Mono<Void> onAuthenticationSuccess(Authentication authentication, WebFilterExchange webFilterExchange) {
        return super.onAuthenticationSuccess(authentication, webFilterExchange);
    }
}
