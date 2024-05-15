package com.lovecloud.global.jwt;


import com.lovecloud.global.jwt.exception.*;
import com.lovecloud.global.jwt.refresh.RefreshToken;
import com.lovecloud.global.jwt.refresh.RefreshTokenRepository;
import com.lovecloud.global.usermanager.JpaUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Component
public class JwtTokenProvider {

    private static final String BEARER_PREFIX = "Bearer ";

    private final String secretKey;
    private final long tokenExpirationTime;
    private final String issuer;

    private final JpaUserDetailsService userDetailsService;
    private final RefreshTokenRepository refreshTokenRepository;

    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;
    private Key key;

    public JwtTokenProvider(
            @Value("${jwt.secret-key}") String secretKey,
            @Value("${jwt.token-expiration-time}") long tokenExpirationTime,
            @Value("${jwt.issuer}") String issuer,
            JpaUserDetailsService userDetailsService,
            RefreshTokenRepository refreshTokenRepository
        ) {

        this.secretKey = secretKey;
        this.tokenExpirationTime = tokenExpirationTime;
        this.issuer = issuer;
        this.userDetailsService = userDetailsService;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @PostConstruct
    public void init() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * AccessToken을 발급하는 메서드
     *
     * @param username AccessToken을 발급받을 사용자의 ID
     * @return 생성된 AccessToken 문자열
     */
    public String createAccessToken(String username) {

        return Jwts.builder()
                .setSubject(username)
                .setIssuer(issuer)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpirationTime))
                .signWith(key, signatureAlgorithm)
                .compact();
    }

    /**
     * HttpServletRequest 에서 Authorization 헤더를 추출하여 토큰을 해결하는 메서드
     *
     * @param request HttpServletRequest 객체
     * @return 추출된 토큰 문자열
     */
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(BEARER_PREFIX.length());
        }
        return null;
    }


    /**
     * 토큰을 사용하여 클레임을 생성하고, 이를 기반으로 사용자 객체를 만들어 인증(Authentication) 객체를 반환하는 메서드
     *
     * @param token 토큰 문자열
     * @return 인증(Authentication) 객체
     */
    public Authentication getAuthentication(String token) {

        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "",
                userDetails.getAuthorities());
    }

    /**
     * 주어진 토큰에서 사용자 이름을 추출하는 메서드
     *
     * @param token 추출할 사용자 이름이 포함된 토큰 문자열
     * @return 추출된 사용자 이름
     */
    public String getUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * 주어진 사용자 이름을 기반으로 RefreshToken을 생성하는 메서드
     *
     * @param username RefreshToken을 발급받을 사용자의 이름
     * @return 생성된 Refresh Token 문자열
     */
    public String createRefreshToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuer(issuer)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(Date.from(Instant.now().plus(14, ChronoUnit.DAYS)))
                .signWith(key, signatureAlgorithm)
                .compact();
    }

    /**
     * 주어진 RefreshToken을 사용하여 AccessToken을 재발급하는 메서드
     *
     * @param refreshToken AccessToken 재발급에 사용될 RefreshToken
     * @return 재발급된 AccessToken 문자열
     */
    public String reCreateAccessToken(String refreshToken) {

        String username = getUsername(refreshToken);
        Optional<RefreshToken> existingToken = refreshTokenRepository.findByUsername(username);

        if (existingToken.isPresent()) {
            String existRefreshToken = existingToken.get().getRefreshToken();

            if (!refreshToken.equals(existRefreshToken)) {
                throw new InvalidRefreshTokenException();
            }
        } else {
            throw new RefreshTokenNotFoundException();
        }
        return createAccessToken(username);
    }

    public boolean validateToken(String token) {
        try {
            if (token == null || !token.startsWith(BEARER_PREFIX)) {
                return false;
            }

            token = token.substring(BEARER_PREFIX.length()).trim();

            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            return !claims.getBody().getExpiration().before(new Date());

        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.error("잘못된 JWT 서명: ", e);
            throw new MalformedJwtException();
        } catch (ExpiredJwtException e) {
            log.error("만료된 JWT 토큰: ", e);
            throw new ExpiredJwtException();
        } catch (UnsupportedJwtException e) {
            log.error("지원되지 않는 JWT 토큰: ", e);
            throw new UnsupportedJwtException();
        } catch (IllegalArgumentException e) {
            log.error("JWT 토큰이 잘못되었습니다: ", e);
            throw new IllegalArgumentJwtException();
        }
    }



}
