package com.lovecloud.usermanagement.presentation;

import com.lovecloud.global.usermanager.SecurityUser;
import com.lovecloud.usermanagement.application.CoupleCreationService;
import com.lovecloud.usermanagement.domain.WeddingUser;
import com.lovecloud.usermanagement.presentation.request.CoupleInvitationCodeRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CoupleCreationController {

    private final CoupleCreationService coupleCreationService;

    @PreAuthorize("hasRole('ROLE_WEDDING_USER')")
    @PostMapping("/couples")
    public ResponseEntity<Void> createCouple(
            @AuthenticationPrincipal SecurityUser securityUser,
            @Valid @RequestBody CoupleInvitationCodeRequest request){

        coupleCreationService.createCouple((WeddingUser) securityUser.user(), request.invitationCode());

        return ResponseEntity.ok().build();
    }
}
