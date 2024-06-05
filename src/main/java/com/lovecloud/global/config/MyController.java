package com.lovecloud.global.config;

import com.lovecloud.global.usermanager.SecurityUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
public class MyController {

    @PreAuthorize("hasRole('ROLE_WEDDING_USER')")
    @GetMapping("/wedding-user")
    public ResponseEntity<String> weddingUserEndpoint(@AuthenticationPrincipal SecurityUser securityUser){

        log.info("WeddingUser: {}", securityUser.getUsername());
        log.info("WeddingUser: {}", securityUser.isEnabled());
        log.info("WeddingUser: {}", securityUser.isAccountNonExpired());
        log.info("WeddingUser: {}", securityUser.user().getId());
        log.info("WeddingUser: {}", securityUser.user().getEmail());
        log.info("WeddingUser: {}", securityUser.user().getUserRole());


        return ResponseEntity.ok("WeddingUser Access");
    }

    @PreAuthorize("hasRole('ROLE_GUEST')")
    @PostMapping("/guest")
    public ResponseEntity<String> guestEndpoint() {
        return ResponseEntity.ok("Guest Access");
    }
}