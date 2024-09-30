package com.lovecloud.auth.application;

import com.lovecloud.auth.application.command.GuestSignUpCommand;
import com.lovecloud.auth.domain.GuestRepository;
import com.lovecloud.auth.domain.GuestValidator;
import com.lovecloud.auth.domain.Password;
import com.lovecloud.auth.presentation.request.GuestSignInRequest;
import com.lovecloud.blockchain.application.WalletCreationService;
import com.lovecloud.blockchain.domain.Wallet;
import com.lovecloud.global.crypto.CustomPasswordEncoder;
import com.lovecloud.global.jwt.JwtTokenProvider;
import com.lovecloud.global.jwt.dto.JwtTokenDto;
import com.lovecloud.global.jwt.refresh.RefreshTokenServiceImpl;
import com.lovecloud.usermanagement.domain.Guest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GuestAuthService {

    private final GuestRepository guestRepository;
    private final GuestValidator validator;
    private final CustomPasswordEncoder passwordEncoder;
    private final RefreshTokenServiceImpl refreshTokenService;
    private final AuthService authService;
    private final WalletCreationService walletCreationService;

    /**
     * GuestSignupCommand를 기반으로 회원을 생성하고, 토큰을 발급하는 메서드
     *
     * @param command 회원 가입에 필요한 정보를 담은  GuestSignupCommand 객체
     * @return 토큰에 대한 JwtTokenDto 객체
     * @throws
     */
    @Transactional
    public JwtTokenDto signUp(GuestSignUpCommand command) {

        Password password = passwordEncoder.encode(command.password());
        Guest user = command.toGuest(password);
        user.signUp(validator);

        guestRepository.save(user);

        Wallet wallet = walletCreationService.saveWallet();
        user.assignWallet(wallet);

        guestRepository.save(user);

        JwtTokenDto jwtTokenDto = authService.createJwtTokenDto(user.getEmail(), user.getUserRole());
        refreshTokenService.createRefreshToken(jwtTokenDto, user.getEmail(), user.getUserRole());

        return jwtTokenDto;
    }


    /**
     * 로그인을 처리하고, 토큰을 발급하는 메서드
     *
     * @param request 로그인에 필요한 정보를 담은 GuestSignInRequest 객체
     * @return 토큰에 대한 JwtTokenDto 객체
     * @throws
     */
    @Transactional
    public JwtTokenDto signIn(GuestSignInRequest request){
        Guest user = guestRepository.getByEmailOrThrow(request.email());
        user.signIn(request.password(), passwordEncoder);

        JwtTokenDto jwtTokenDto = authService.createJwtTokenDto(user.getEmail(), user.getUserRole());
        refreshTokenService.createRefreshToken(jwtTokenDto, user.getEmail(), user.getUserRole());

        return jwtTokenDto;
    }

}
