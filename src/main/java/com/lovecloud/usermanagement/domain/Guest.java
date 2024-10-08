package com.lovecloud.usermanagement.domain;

import com.lovecloud.auth.domain.GuestValidator;
import com.lovecloud.auth.domain.Password;
import com.lovecloud.blockchain.domain.Wallet;
import com.lovecloud.blockchain.exception.WalletAlreadyAssignedException;
import com.lovecloud.global.crypto.CustomPasswordEncoder;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "guest")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Guest extends User {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;

    @Column(name = "phone_number", nullable = false, length = 100)
    private String phoneNumber;

    @Embedded
    private Password password;

    @Builder
    public Guest(String email, String name, UserRole userRole, String phoneNumber,
            Password password) {
        super(email, name, userRole);
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public void signUp(GuestValidator validator){
        validator.validateDuplicateEmail(this.getEmail());
    }

    public String getPassword() {
        return password.getEncryptedPassword();
    }

    public void signIn(String rawPassword, CustomPasswordEncoder passwordEncoder){
        this.password.validatePassword(rawPassword, passwordEncoder);
    }

    public void assignWallet(Wallet wallet){
        if(this.wallet != null){
            throw new WalletAlreadyAssignedException();
        }
        this.wallet = wallet;
    }
}
