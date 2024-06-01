package com.lovecloud.global.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


/**
 * JwtAuthenticationFilter
 * JWT 토큰을 검증하고 유효한 경우 인증 정보를 설정하는 필터
 * 각 요청당 한번만 실행하기를 보장하는 OncePerRequestFilter를 상속
 * JWT를 검증한 후 인증된 Authentication 객체를 SecurityContext에 저장
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = jwtTokenProvider.resolveToken(request);
        log.debug("Resolved Token: {}", token);
        if (token != null && jwtTokenProvider.validateToken(token)) {
            log.debug("Token is valid");
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(null, token);
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            log.debug("Authenticated: {}", authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        log.debug("Token is invalid");
        filterChain.doFilter(request, response);
    }
}
