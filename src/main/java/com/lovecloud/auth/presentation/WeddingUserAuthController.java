package com.lovecloud.auth.presentation;


import com.lovecloud.auth.application.WeddingUserAuthService;
import com.lovecloud.auth.presentation.request.WeddingSignInRequest;
import com.lovecloud.auth.presentation.request.WeddingSignUpRequest;
import com.lovecloud.global.jwt.dto.JwtTokenDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth/wedding-user")
@RequiredArgsConstructor
public class WeddingUserAuthController {

    private final WeddingUserAuthService weddingUserAuthService;

    //TODO: return 값 다시 체크

    /**
     * 회원 가입을 처리하는 메서드
     *
     * @param request 회원 가입에 필요한 정보를 담은 WeddingSignUpRequest 객체
     * @return 회원 가입 결과에 대한 JwtTokenDto 객체
     */
    @PostMapping("/sign-up")
    public ResponseEntity<JwtTokenDto> signUp(@Valid @RequestBody WeddingSignUpRequest request){
        JwtTokenDto jwtTokenDto = weddingUserAuthService.signUp(request.toCommand());

        log.info("유저가 생성되었습니다. {}", request.email());
        return ResponseEntity.ok(jwtTokenDto);
    }


    /**
     * 로그인을 처리하는 메서드
     *
     * @param request 로그인에 필요한 정보를 담은 WeddingSignInRequest 객체
     * @return 로그인 결과에 대한 JwtTokenDto 객체
     */
    @PostMapping("/sign-in")
    public ResponseEntity<JwtTokenDto> signIn(@Valid @RequestBody WeddingSignInRequest request){
        JwtTokenDto jwtTokenDto = weddingUserAuthService.signIn(request);

        log.info("유저가 로그인 되었습니다. {}", request.email());
        return ResponseEntity.ok(jwtTokenDto);
    }


}
