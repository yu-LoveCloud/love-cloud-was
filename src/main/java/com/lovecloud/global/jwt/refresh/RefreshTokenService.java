package com.lovecloud.global.jwt.refresh;

import com.lovecloud.global.jwt.dto.JwtTokenDto;
import com.lovecloud.usermanagement.domain.UserRole;

public interface RefreshTokenService {

    void createRefreshToken(JwtTokenDto jwtTokenDto, String username, UserRole userRole);

    void deleteRefreshToken(JwtTokenDto jwtTokenDto, String username, UserRole userRole);

    String reCreateAccessTokenByRefreshToken(String refreshToken);

    String reCreateRefreshTokenByRefreshToken(String refreshToken);
}
