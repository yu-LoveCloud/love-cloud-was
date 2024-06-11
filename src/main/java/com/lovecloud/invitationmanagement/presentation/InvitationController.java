package com.lovecloud.invitationmanagement.presentation;

import com.lovecloud.global.usermanager.SecurityUser;
import com.lovecloud.invitationmanagement.application.InvitationCreateService;
import com.lovecloud.invitationmanagement.presentation.request.CreateInvitationRequest;
import com.lovecloud.usermanagement.application.CoupleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequiredArgsConstructor
@RequestMapping("/invitations")
@RestController
public class InvitationController {

    private final InvitationCreateService invitationCreateService;
    private final CoupleService coupleService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_WEDDING_USER')")
    public ResponseEntity<Long> invitationAdd(@Valid @RequestBody CreateInvitationRequest request,
                                              @AuthenticationPrincipal SecurityUser securityUser)
    {

        final Long invitationId = invitationCreateService.addInvitation(request.toCommand());
        coupleService.updateCoupleInvitation(securityUser.user().getId(), invitationId);

        return ResponseEntity.created(URI.create("/invitations/" + invitationId)).build();
    }

}
