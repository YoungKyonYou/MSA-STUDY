package com.youyk.userservice.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.youyk.userservice.dto.UserDto;
import com.youyk.userservice.service.UserService;
import com.youyk.userservice.vo.RequestLogin;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.KEY;
import io.jsonwebtoken.Jwts.SIG;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security에서 UsernamePasswordAuthenticationFilter는
 * 기본적으로 "/login" 경로에서 동작하도록 설계되어 있습니다.
 * 이는 내부적으로 setFilterProcessesUrl() 메서드를 통해 /login을 처리하는 필터임을 지정하고 있습니다.
 */
@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private UserService userService;
    private Environment env;
    private String bearer = "Bearer %s";


    public AuthenticationFilter(AuthenticationManager authenticationManager,
                                UserService userService, Environment env) {
        super.setAuthenticationManager(authenticationManager);
        this.userService = userService;
        this.env = env;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            RequestLogin creds = new ObjectMapper().readValue(request.getInputStream(), RequestLogin.class);


            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    creds.email(), creds.password(), new ArrayList<>());

            return getAuthenticationManager().authenticate(token);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        String userName = ((User) authResult.getPrincipal()).getUsername();
        UserDto userDto = userService.getUserDetailsByEmail(userName);



        String token = Jwts.builder()
                .subject(userDto.userId())
                .expiration(new Date(System.currentTimeMillis() + Long.parseLong(
                        Objects.requireNonNull(env.getProperty("token.expiration_time")))))
                .signWith(getSigningKey(), SIG.HS512)
                .compact();

        response.addHeader("Authorization", bearer.formatted(token));
        response.addHeader("userId", userDto.userId());
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Base64.getDecoder().decode(env.getProperty("token.secret"));
        return new SecretKeySpec(keyBytes, 0, keyBytes.length, "HmacSHA512");
    }
}
