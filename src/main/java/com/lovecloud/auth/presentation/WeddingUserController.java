package com.lovecloud.auth.presentation;


import com.lovecloud.auth.application.WeddingAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth/wedding-user")
@RequiredArgsConstructor
public class WeddingUserController {

    private final WeddingAuthService weddingAuthService;


}
