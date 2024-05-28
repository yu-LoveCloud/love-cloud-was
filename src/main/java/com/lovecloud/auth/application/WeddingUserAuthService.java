package com.lovecloud.auth.application;

import com.lovecloud.auth.application.command.WeddingSignUpCommand;
import com.lovecloud.auth.domain.Password;
import com.lovecloud.auth.domain.WeddingUserRepository;
import com.lovecloud.auth.domain.WeddingUserValidator;
import com.lovecloud.auth.presentation.request.WeddingSignInRequest;
import com.lovecloud.global.crypto.CustomPasswordEncoder;
import com.lovecloud.global.jwt.JwtTokenProvider;
import com.lovecloud.global.jwt.dto.JwtTokenDto;
import com.lovecloud.global.jwt.refresh.RefreshTokenServiceImpl;
import com.lovecloud.usermanagement.domain.WeddingUser;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class WeddingUserAuthService {

    private final WeddingUserRepository weddingUserRepository;
    private final WeddingUserValidator validator;
    private final CustomPasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenServiceImpl refreshTokenService;
    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * WeddingSignupCommand를 기반으로 회원을 생성하고, 토큰을 발급하는 메서드
     *
     * @param command 회원 가입에 필요한 정보를 담은  WeddingSignupCommand 객체
     * @return 토큰에 대한 JwtTokenDto 객체
     * @throws
     */
    @Transactional
    public JwtTokenDto signUp(WeddingSignUpCommand command) {

        Password password = passwordEncoder.encode(command.password());
        WeddingUser user = command.toWeddingUser(password);
        user.signUp(validator);

        weddingUserRepository.save(user);

        JwtTokenDto jwtTokenDto = createJwtTokenDto(user);
        refreshTokenService.createRefreshToken(jwtTokenDto, user.getEmail());

        return jwtTokenDto;
    }

    /**
     * 로그인을 처리하고, 토큰을 발급하는 메서드
     *
     * @param request 로그인에 필요한 정보를 담은 WeddingSignInRequest 객체
     * @return 토큰에 대한 JwtTokenDto 객체
     * @throws
     */
    @Transactional
    public JwtTokenDto signIn(WeddingSignInRequest request){
        WeddingUser user = weddingUserRepository.getByEmail(request.email());
        user.signIn(request.password(), passwordEncoder);

        JwtTokenDto jwtTokenDto = createJwtTokenDto(user);
        refreshTokenService.createRefreshToken(jwtTokenDto, user.getEmail());

        return jwtTokenDto;
    }

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
     * @param user
     * @return JwtTokenDto
     */
    public JwtTokenDto createJwtTokenDto(WeddingUser user){

        String accessToken = jwtTokenProvider.createAccessToken(user.getEmail());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getEmail());

        return JwtTokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}


