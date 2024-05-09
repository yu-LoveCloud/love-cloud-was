package com.lovecloud.invitationmanagement.presentation;

import com.lovecloud.invitationmanagement.presentation.request.CreateInvitationRequest;
import com.lovecloud.invitationmanagement.application.InvitationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/invitations")
@RestController
public class InvitationController {

    private final InvitationService invitationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Long> invitationAdd(@AuthenticationPrincipal Long memberId,
                                              @RequestBody CreateInvitationRequest request) {

        Long id = invitationService.addInvitation(memberId, request);

        return ResponseEntity.ok(id);
    }

}
