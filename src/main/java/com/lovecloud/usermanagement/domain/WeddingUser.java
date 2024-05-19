package com.lovecloud.usermanagement.domain;

import com.lovecloud.auth.domain.Password;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "wedding_user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WeddingUser extends User {

    @Column(name = "phone_number", nullable = false, length = 100)
    private String phoneNumber;

    @Column(name = "password", nullable = false, length = 100)
    private Password password;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_status", nullable = false, length = 100)
    private AccountStatus accountStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "wedding_role", nullable = false, length = 100)
    private WeddingRole weddingRole;

    @Column(name = "oauth_id", nullable = true, length = 100)
    private String oauthId;

    @Column(name = "oauth", nullable = true, length = 100)
    private String oauth;

    @Column(name = "invitation_code", nullable = true, length = 100)
    private String invitationCode;

    @Builder
    public WeddingUser(String email, String name, UserRole userRole, String phoneNumber,
            Password password, AccountStatus accountStatus, WeddingRole weddingRole, String oauthId,
            String oauth, String invitationCode) {
        super(email, name, userRole);
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.accountStatus = accountStatus;
        this.weddingRole = weddingRole;
        this.oauthId = oauthId;
        this.oauth = oauth;
        this.invitationCode = invitationCode;
    }


}
