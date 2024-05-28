package com.lovecloud.auth.application;

import com.lovecloud.global.jwt.JwtTokenProvider;
import com.lovecloud.global.jwt.dto.JwtTokenDto;
import com.lovecloud.usermanagement.domain.WeddingUser;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, Object> redisTemplate;


    /**
     * 로그아웃을 처리하는 메서드
     *  - AccessToken을 Redis에 저장하여 블랙리스트 처리
     *  - AccessToken의 만료시간까지만 저장
     * @param token 로그아웃할 AccessToken
     */
    @Transactional
    public void signOut(String token){
        Date expirationDate = jwtTokenProvider.getExpirationDate(token);

        redisTemplate.opsForValue().set(token, "blacklisted", expirationDate.getTime() - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    /**
     * User email로 JwtTokenDto를 생성하는 메서드
     *
     * @param email User email
     * @return JwtTokenDto
     */
    public JwtTokenDto createJwtTokenDto(String email){

        String accessToken = jwtTokenProvider.createAccessToken(email);
        String refreshToken = jwtTokenProvider.createRefreshToken(email);

        return JwtTokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

}
