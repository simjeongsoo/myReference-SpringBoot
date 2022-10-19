package com.sim.jwt.jwt;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    //--TokenProvider, JwtFilter 를 SecurityConfig에 적용할 때 사용할 클래스--//

    // SecurityConfigurerAdapter 를 상속받고
    private TokenProvider tokenProvider;
    // tokenProvider 주입
    public JwtSecurityConfig(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // JwtFilter 를 통해
        JwtFilter customFilter = new JwtFilter(tokenProvider);
        // Security 로직에 필터 등록
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
