package com.lovecloud.global.jwt.refresh;

import com.lovecloud.global.jwt.dto.JwtTokenDto;

public interface RefreshTokenService {

    void createRefreshToken(JwtTokenDto jwtTokenDto, String username);

    void deleteRefreshToken(JwtTokenDto jwtTokenDto, String username);

    String reCreateAccessTokenByRefreshToken(String refreshToken);

    String reCreateRefreshTokenByRefreshToken(String refreshToken);
}
