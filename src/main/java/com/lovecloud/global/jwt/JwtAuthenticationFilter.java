package com.lovecloud.global.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


/**
 * JwtAuthenticationFilter
 * JWT 토큰을 검증하고 유효한 경우 인증 정보를 설정하는 필터
 * 각 요청당 한번만 실행하기를 보장하는 OncePerRequestFilter를 상속
 * JWT를 검증한 후 인증된 Authentication 객체를 SecurityContext에 저장
 **/
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = jwtTokenProvider.resolveToken(request);

        if (token != null && jwtTokenProvider.validateToken(token)) {
            try {
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (UsernameNotFoundException e) {

                logger.error("Authentication error: ", e);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication failed");
                return;
            } catch (Exception e) {

                logger.error("Unexpected error: ", e);
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
