package com.lovecloud.invitationmanagement.presentation;

import com.lovecloud.invitationmanagement.application.InvitationCreateService;
import com.lovecloud.invitationmanagement.presentation.request.CreateInvitationRequest;
import com.lovecloud.usermanagement.application.CoupleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequiredArgsConstructor
@RequestMapping("/invitations")
@RestController
public class InvitationController {

    private final InvitationCreateService invitationCreateService;
    private final CoupleService coupleService;

    //TODO: 인증 및 유저 정보 가져오기 구현
    //@Auth(role = Role.USER)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Long> invitationAdd(@Valid @RequestBody CreateInvitationRequest request,
                                              Long userId )
    {

        final Long invitationId = invitationCreateService.addInvitation(request.toCommand());
        coupleService.updateCoupleInvitation(userId,invitationId);

        return ResponseEntity.created(URI.create("/invitations/" + invitationId)).build();
    }

}
