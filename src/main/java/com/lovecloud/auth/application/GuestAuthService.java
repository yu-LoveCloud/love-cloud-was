package com.lovecloud.auth.application;

import com.lovecloud.auth.domain.GuestRepository;
import com.lovecloud.auth.domain.GuestValidator;
import com.lovecloud.global.crypto.CustomPasswordEncoder;
import com.lovecloud.global.jwt.JwtTokenProvider;
import com.lovecloud.global.jwt.refresh.RefreshTokenServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GuestAuthService {

    private final GuestRepository guestRepository;
    private final GuestValidator validator;
    private final CustomPasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenServiceImpl refreshTokenService;

    /**
     * GuestSignupCommand를 기반으로 회원을 생성하고, 토큰을 발급하는 메서드
     *
     * @param command 회원 가입에 필요한 정보를 담은  GuestSignupCommand 객체
     * @return 토큰에 대한 JwtTokenDto 객체
     * @throws
     */
}
