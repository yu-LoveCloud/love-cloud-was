package com.lovecloud.invitationmanagement.presentation;

import com.lovecloud.invitationmanagement.application.InvitationService;
import com.lovecloud.invitationmanagement.presentation.request.CreateInvitationRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/invitations")
@RestController
public class InvitationController {

    private final InvitationService invitationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Long> invitationAdd(@Valid @RequestBody CreateInvitationRequest request) {

        final Long id = invitationService.addInvitation(request);

        return ResponseEntity.ok(id);
    }

}
