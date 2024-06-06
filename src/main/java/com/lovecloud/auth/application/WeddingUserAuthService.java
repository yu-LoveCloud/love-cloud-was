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
    private final RefreshTokenServiceImpl refreshTokenService;
    private final AuthService authService;


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

        JwtTokenDto jwtTokenDto = authService.createJwtTokenDto(user.getEmail(), user.getUserRole());
        refreshTokenService.createRefreshToken(jwtTokenDto, user.getEmail(), user.getUserRole());

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
        WeddingUser user = weddingUserRepository.getByEmailOrThrow(request.email());
        user.signIn(request.password(), passwordEncoder);

        JwtTokenDto jwtTokenDto = authService.createJwtTokenDto(user.getEmail(), user.getUserRole());
        refreshTokenService.createRefreshToken(jwtTokenDto, user.getEmail(), user.getUserRole());

        return jwtTokenDto;
    }


}


