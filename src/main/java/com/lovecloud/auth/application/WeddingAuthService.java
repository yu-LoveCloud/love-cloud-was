package com.lovecloud.auth.application;

import com.lovecloud.auth.application.command.WeddingSignupCommand;
import com.lovecloud.auth.domain.Password;
import com.lovecloud.global.crypto.CustomPasswordEncoder;
import com.lovecloud.global.jwt.dto.JwtTokenDto;
import com.lovecloud.usermanagement.domain.WeddingUser;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeddingAuthService {

  /*  private final CustomPasswordEncoder passwordEncoder;

    @Transactional
    public JwtTokenDto signUp(WeddingSignupCommand command) {

        Password password = passwordEncoder.encode(command.password());
        WeddingUser user = command.toWeddingUser(password);


        return null;

    }*/
}


