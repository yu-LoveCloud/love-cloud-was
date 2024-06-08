package com.lovecloud.global.config;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MyController {

    @PreAuthorize("hasRole('ROLE_WEDDING_USER')")
    @GetMapping("/wedding-user")
    public ResponseEntity<String> weddingUserEndpoint() {
        return ResponseEntity.ok("WeddingUser Access");
    }

    @PreAuthorize("hasRole('ROLE_GUEST')")
    @PostMapping("/guest")
    public ResponseEntity<String> guestEndpoint() {
        return ResponseEntity.ok("Guest Access");
    }
}

