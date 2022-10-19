package com.sim.jwt.controller;

import com.sim.jwt.dto.LoginDto;
import com.sim.jwt.dto.TokenDto;
import com.sim.jwt.jwt.JwtFilter;
import com.sim.jwt.jwt.TokenProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class AuthController {
    //--로그인 API를 추가하기 위한 클래스--//

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    // TokenProvider, AuthenticationManagerBuilder 주입
    public AuthController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<TokenDto> authorize(@Valid @RequestBody LoginDto loginDto) {
        // LoginDto의 username, password를 파라미터로 받고

        // 이를 이용해 UsernamePasswordAuthenticationToken 을 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        // authenticationToken 을 이용해서 Authentication 객체를 생성하기위해 authenticate() 메서드가 실행될때
        // CustomUserDetailsService 클래스의 loadUserByUsername() 메서드가 실행
        // 실행된 결과값을 가지고 Authentication 객체 생성
        Authentication authentication
                = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // authentication 객체를 SecurityContext에 저장하고
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // authentication 객체를 createToken() 메서드를 통해 JWT Token 생성
        String jwt = tokenProvider.createToken(authentication);


        HttpHeaders httpHeaders = new HttpHeaders();
        // JWT Token 을 Response Header 에도 넣어주고
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        // TokenDto를 이용해서 ResponseBody에도 넣어서 리턴
        // 둘다 가능한 방법, 실제 프로젝트에서는 적당히 선택하여 구현
        return new ResponseEntity<>(new TokenDto(jwt), httpHeaders, HttpStatus.OK);
    }

}
