package com.sim.jwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity  // Spring Security 설정 활성화
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http)throws Exception {
        //--spring security 5.7이상에서 더 이상 WebSecurityConfigurerAdapter 사용을 권장하지 않는다.
        // SecurityFilterChain 를 @Bean 으로 등록하는 방식을 권장한다--//
        http
                .authorizeRequests()
                .antMatchers("/api/hello").permitAll()
                .anyRequest().authenticated();

        return http.build();
    }
}
