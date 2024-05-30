package com.lovecloud.auth.presentation;


import com.lovecloud.auth.application.GuestAuthService;
import com.lovecloud.auth.presentation.request.GuestSignUpRequest;
import com.lovecloud.global.jwt.dto.JwtTokenDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth/guest")
@RequiredArgsConstructor
public class GuestAuthController {

    private final GuestAuthService guestAuthService;

    @PostMapping("/sign-up")
    public ResponseEntity<JwtTokenDto> signUp(@Valid @RequestBody GuestSignUpRequest request){
        JwtTokenDto jwtTokenDto = guestAuthService.signUp(request.toCommand());

        log.info("유저가 생성되었습니다. {}", request.email());
        return ResponseEntity.ok(jwtTokenDto);
    }
}
