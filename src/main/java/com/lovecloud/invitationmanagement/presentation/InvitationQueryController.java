package com.lovecloud.invitationmanagement.presentation;

import com.lovecloud.invitationmanagement.query.response.InvitationDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/invitations")
public class InvitationQueryController {

    private final InvitationQueryService invitationQueryService;

    @GetMapping("/{invitationId}")
    public ResponseEntity<InvitationDetailResponse> detailInvitation(@PathVariable Long invitationId) {
        final InvitationDetailResponse invitation = invitationQueryService.findById(invitationId);
        return ResponseEntity.ok(invitation);
    }
}
