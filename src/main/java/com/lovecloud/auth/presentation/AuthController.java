package com.lovecloud.auth.presentation;

import com.lovecloud.global.jwt.JwtTokenProvider;
import com.lovecloud.global.jwt.dto.JwtTokenDto;
import com.lovecloud.global.jwt.refresh.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final RefreshTokenService refreshTokenService;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * RefreshToken을 사용하여 새로운 AccessToken과 RefreshToken을 발급하는 메서드
     *
     * @param refreshTokenHeader 사용할 RefreshToken (Bearer Token 형식)
     * @return 발급된 새로운 AccessToken과 RefreshToken에 대한 TokenDto 객체
     */
    @PostMapping("/refresh-token")
    public ResponseEntity<JwtTokenDto> reCreateToken(
            @RequestHeader("Authorization") String refreshTokenHeader) {

        String refreshToken = refreshTokenHeader.substring(7).trim();

        String newAccessToken = refreshTokenService.reCreateAccessTokenByRefreshToken(refreshToken);
        String newRefreshToken = refreshTokenService.reCreateRefreshTokenByRefreshToken(refreshToken);

        JwtTokenDto jwtTokenDto = JwtTokenDto.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();

        refreshTokenService.createRefreshToken(jwtTokenDto, jwtTokenProvider.getUsername(refreshToken));

        log.info("토큰이 재생성 되었습니다. {}", jwtTokenProvider.getUsername(refreshToken));
        return ResponseEntity.ok(jwtTokenDto);
    }
}
