package com.youyk.userservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
public class WebSecurity {

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http)
            throws Exception {


        // 명시적으로 허용할 url 등록
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(
                                "/users/**",
                                "/h2-console/**"
                        ).permitAll()
                )
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
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable) // 동일 출처로 설정
                );
        return http.build();

    }
}
