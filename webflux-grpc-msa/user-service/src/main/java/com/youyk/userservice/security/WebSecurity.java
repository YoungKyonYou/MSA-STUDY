package com.youyk.userservice.security;

import com.youyk.userservice.r2dbc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity.HeaderSpec.FrameOptionsSpec;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
@EnableWebFluxSecurity
@Configuration
public class WebSecurity {

    private final UserRepository userRepository;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityWebFilterChain filterChain(final ServerHttpSecurity http)
            throws Exception {


        // 명시적으로 허용할 url 등록
        http.csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(authorize -> authorize
                        .pathMatchers("/users/**",
                                "/health_check/**",
                                "/actuator/**").permitAll().anyExchange().authenticated()) // 허용할 URL 패턴
                /**
                 * defaultsDisabled는
                 * Spring Security는 기본적으로 여러 보안 관련 헤더(예: X-Content-Type-Options, X-Frame-Options, X-XSS-Protection 등)를 자동으로 설정합니다.
                 * .defaultsDisabled()를 사용하면 이러한 기본 설정을 비활성화하고, 필요한 헤더만 수동으로 추가하거나 수정할 수 있습니다.
                 *
                 * freamOptions는
                 * 이 설정은 X-Frame-Options 헤더를 설정하여 클릭재킹(Clickjacking) 공격을 방지하는 데 사용됩니다.
                 *
                 * sameOrigin은 동일 출처(Same Origin)에서만 iframe을 통해 콘텐츠를 로드할 수 있도록 제한합니다.
                 * 즉, 동일한 도메인에서 로드된 페이지에서만 iframe으로 감쌀 수 있게 하는 설정입니다.
                 * 예를 들어, 같은 도메인의 페이지가 자신의 페이지를 iframe에 포함할 수 있지만, 다른 도메인의 페이지는 포함할 수 없도록 제한합니다.
                 */
                .headers(headers -> headers// 기본 헤더 설정 비활성화
                        .frameOptions(FrameOptionsSpec::disable) // 동일 출처로 설정
                ).addFilterAt(jwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION);

        return http.build();

    }



}
