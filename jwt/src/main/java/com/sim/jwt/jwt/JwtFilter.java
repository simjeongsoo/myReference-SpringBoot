package com.sim.jwt.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JwtFilter extends GenericFilterBean {
    //--JWT를 위한 커스텀 필터를 만들기 위한 JwtFilter 클래스--//
    //--GenericFilterBean을 extends 해서 doFilter Override, 실제 필터링 로직은 doFilter 내부에 작성--//

    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);

    public static final String AUTHORIZATION_HEADER = "Authorization";

    private TokenProvider tokenProvider;// TokenProvider 주입

    public JwtFilter(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        //--doFilter는 jwt토큰의 인증 정보를 현재 실행중인 SecurityContext에 저장하는 역할 수행--//

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        // resolveToken() 에서 토큰정보를 받옴
        String jwt = resolveToken(httpServletRequest);
        String requestURI = httpServletRequest.getRequestURI();

        // 받아온 jwt 토큰의 유효성 검사
        if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
            // 토큰이 정상이라면 authentication 정보를 받아와
            Authentication authentication = tokenProvider.getAuthentication(jwt);
            // SecurityContext에 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);
            logger.debug("Security Context에 '{}' 인증정보를 저장했습니다, uri: {}", authentication.getName(), requestURI);
        } else {
            logger.debug("유효한 JWT 토큰이 없습니다, uri: {}", requestURI);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String resolveToken(HttpServletRequest request) {
        //--Request Header에서 토큰 정보를 꺼내오기 위한 resolveToken 메소드 추가--//
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }

}
