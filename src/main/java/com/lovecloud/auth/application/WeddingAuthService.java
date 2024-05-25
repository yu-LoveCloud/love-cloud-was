package com.lovecloud.auth.application;

import com.lovecloud.auth.application.command.WeddingSignupCommand;
import com.lovecloud.auth.domain.Password;
import com.lovecloud.auth.domain.WeddingUserRepository;
import com.lovecloud.auth.domain.WeddingUserValidator;
import com.lovecloud.global.crypto.CustomPasswordEncoder;
import com.lovecloud.global.jwt.JwtTokenProvider;
import com.lovecloud.global.jwt.dto.JwtTokenDto;
import com.lovecloud.global.jwt.refresh.RefreshTokenServiceImpl;
import com.lovecloud.usermanagement.domain.WeddingUser;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeddingAuthService {

    private final WeddingUserRepository weddingUserRepository;
    private final WeddingUserValidator validator;
    private final CustomPasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenServiceImpl refreshTokenService;

    /**
     * WeddingSignupCommand를 기반으로 회원을 생성하고, 토큰을 발급하는 메서드
     *
     * @param command 회원 가입에 필요한 정보를 담은  WeddingSignupCommand 객체
     * @return 토큰에 대한 JwtTokenDto 객체
     * @throws
     */
    @Transactional
    public JwtTokenDto signUp(WeddingSignupCommand command) {

        Password password = passwordEncoder.encode(command.password());
        WeddingUser user = command.toWeddingUser(password);
        user.signup(validator);

        weddingUserRepository.save(user);

        JwtTokenDto jwtTokenDto = createJwtTokenDto(user);
        refreshTokenService.createRefreshToken(jwtTokenDto, user.getEmail());

        return jwtTokenDto;
    }

    public JwtTokenDto signIn(WeddingSignupCommand command){
        WeddingUser user = weddingUserRepository.getByEmail(command.email());
        return null;
    }

    /**
     * Member username으로 JwtTokenDto를 생성하는 메서드
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


