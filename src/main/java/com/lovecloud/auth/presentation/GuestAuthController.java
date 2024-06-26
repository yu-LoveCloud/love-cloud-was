package com.lovecloud.auth.presentation;


import com.lovecloud.auth.application.GuestAuthService;
import com.lovecloud.auth.presentation.request.GuestSignInRequest;
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

    /**
     * 회원 가입을 처리하는 메서드
     *
     * @param request 회원 가입에 필요한 정보를 담은 GuestSignUpRequest 객체
     * @return 회원 가입 결과에 대한 JwtTokenDto 객체
     */
    @PostMapping("/sign-up")
    public ResponseEntity<JwtTokenDto> signUp(@Valid @RequestBody GuestSignUpRequest request){
        JwtTokenDto jwtTokenDto = guestAuthService.signUp(request.toCommand());

        log.info("유저가 생성되었습니다. {}", request.email());
        return ResponseEntity.ok(jwtTokenDto);
    }

    /**
     * 로그인을 처리하는 메서드
     *
     * @param request 로그인에 필요한 정보를 담은 GuestSignInRequest 객체
     * @return 로그인 결과에 대한 JwtTokenDto 객체
     */
    @PostMapping("/sign-in")
    public ResponseEntity<JwtTokenDto> signIn(@Valid @RequestBody GuestSignInRequest request){
        JwtTokenDto jwtTokenDto = guestAuthService.signIn(request);

        log.info("유저가 로그인 되었습니다. {}", request.email());
        return ResponseEntity.ok(jwtTokenDto);
    }
}
