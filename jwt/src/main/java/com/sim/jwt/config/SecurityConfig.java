package com.sim.jwt.config;

import com.sim.jwt.jwt.JwtAccessDeniedHandler;
import com.sim.jwt.jwt.JwtAuthenticationEntryPoint;
import com.sim.jwt.jwt.JwtSecurityConfig;
import com.sim.jwt.jwt.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity  // Spring Security 설정 활성화
@EnableGlobalMethodSecurity(prePostEnabled = true)  // @PreAuthorize 어노테이션을 메소드 단위로 추가하기 위해 적용
public class SecurityConfig {
    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    public SecurityConfig(TokenProvider tokenProvider,
                          JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
                          JwtAccessDeniedHandler jwtAccessDeniedHandler) {
        this.tokenProvider = tokenProvider;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        //--PasswordEncoder 는 BCryptPasswordEncoder() 를 사용--//
//        return new BCryptPasswordEncoder();
//    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        //--h2-console 하위의 모든 요청들과 파비콘 관련 요청은 Spring Security 로직을 수행하지 않고 접근설정--//
//        return (web -> web
//                .ignoring()
//                .antMatchers(
//                        "/h2-console/**"
//                        , "/favicon.ico")
//        );
        return (web) -> web.ignoring().antMatchers("/h2-console/**"
                , "/favicon.ico");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        //--spring security 5.7이상에서 더 이상 WebSecurityConfigurerAdapter 사용을 권장하지 않는다.
        // SecurityFilterChain 를 @Bean 으로 등록하는 방식을 권장한다--//
        httpSecurity
                // token을 사용하는 방식이기 때문에 csrf를 disable
                .csrf().disable()

                // Exception을 핸들링 할때 만들어둔 클래스 추가
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                // enable h2-console
                .and()
                .headers()
                .frameOptions()
                .sameOrigin()

                // 세션을 사용하지 않기 때문에 STATELESS로 설정
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                // 접근 권한 설정
                .and()
                .authorizeRequests()
                .antMatchers("/api/hello").permitAll()
                .antMatchers("/api/authenticate").permitAll() // 토큰이 없는 상태의 request
                .antMatchers("/api/signup").permitAll()         // 로그인,회원가입 permitAll()
                .anyRequest().authenticated()

                // JwtFilter를 addFilterBefore로 등록했던 JwtSecurityConfig 클래스 적용
                .and()
                .apply(new JwtSecurityConfig(tokenProvider));

        return httpSecurity.build();
    }
}
