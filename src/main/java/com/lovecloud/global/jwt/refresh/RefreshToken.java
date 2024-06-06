package com.lovecloud.global.jwt.refresh;

import com.lovecloud.usermanagement.domain.UserRole;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refresh_token_id")
    private Long id;

    @Column(nullable = false, length = 60)
    private String username;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole userRole;

    @Column(nullable = false)
    private String refreshToken;

    @Builder
    public RefreshToken(Long id, String refreshToken, String username, UserRole userRole) {
        this.id = id;
        this.refreshToken = refreshToken;
        this.username = username;
        this.userRole = userRole;
    }
}

