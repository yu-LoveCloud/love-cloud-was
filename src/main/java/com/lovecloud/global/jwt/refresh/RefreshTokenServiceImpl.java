package com.lovecloud.global.jwt.refresh;


import com.lovecloud.global.jwt.JwtTokenProvider;
import com.lovecloud.global.jwt.dto.JwtTokenDto;
import com.lovecloud.global.jwt.exception.RefreshTokenNotFoundException;
import com.lovecloud.usermanagement.domain.UserRole;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    /**
     * RefreshToken을 새로 발행는 메서드
     *
     * @param
     * @return void
     */
    @Transactional
    @Override
    public void createRefreshToken(JwtTokenDto jwtTokenDto, String username, UserRole userRole) {

        RefreshToken refreshToken = RefreshToken.builder()
                .refreshToken(jwtTokenDto.getRefreshToken())
                .username(username)
                .userRole(userRole)
                .build();

        if (refreshTokenRepository.findByUsernameAndUserRole(username, userRole).isPresent()) {
            refreshTokenRepository.deleteByUsernameAndUserRole(username, userRole);
        }
        refreshTokenRepository.save(refreshToken);
        log.info("create new refresh token. username : {}, userRole : {}", username, userRole);
    }

    /**
     * RefreshToken을 삭제하는 메서드
     *
     * @param
     * @return void
     */
    @Transactional
    @Override
    public void deleteRefreshToken(JwtTokenDto jwtTokenDto, String username, UserRole userRole) {

        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByUsernameAndUserRole(username, userRole);

        if (refreshToken.isPresent()) {
            refreshTokenRepository.delete(refreshToken.get());
            log.info("delete refresh token. username : {}, userRole : {}", username, userRole);
        } else {
            throw new RefreshTokenNotFoundException();
        }

    }


    /**
     * 주어진 RefreshToken을 사용하여 새로운 AccessToken을 재발급하는 메서드
     *
     * @param refreshToken 재발급에 사용될 RefreshToken
     * @return 재발급된 AccessToken 문자열
     */
    @Override
    public String reCreateAccessTokenByRefreshToken(String refreshToken) {
        if (jwtTokenProvider.validateToken(refreshToken)) {
            return jwtTokenProvider.reCreateAccessToken(refreshToken);
        }
        return null;
    }


    /**
     * 주어진 RefreshToken을 사용하여 새로운 RefreshToken을 발급하는 메서드
     *
     * @param refreshToken 재발급에 사용될 RefreshToken
     * @return 재발급된 RefreshToken 문자열
     */
    @Override
    public String reCreateRefreshTokenByRefreshToken(String refreshToken) {
        if (jwtTokenProvider.validateToken(refreshToken)) {
            String username = jwtTokenProvider.getUsername(refreshToken);
            UserRole userRole = jwtTokenProvider.getUserRole(refreshToken);
            return jwtTokenProvider.createRefreshToken(username, userRole);
        }
        return null;
    }
}