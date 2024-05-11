package com.lovecloud.invitationmanagement.application;

import com.lovecloud.invitationmanagement.domain.Invitation;
import com.lovecloud.invitationmanagement.domain.InvitationImage;
import com.lovecloud.invitationmanagement.presentation.request.CreateInvitationRequest;
import com.lovecloud.invitationmanagement.repository.InvitationImageRepository;
import com.lovecloud.invitationmanagement.repository.InvitationRepository;
import com.lovecloud.usermanagement.application.CoupleService;
import com.lovecloud.usermanagement.domain.Couple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class InvitationService {
    private final InvitationRepository invitationRepository;
    private final InvitationImageRepository invitationImageRepository;
    private final CoupleService coupleService;

    public Long addInvitation(final CreateInvitationRequest request) {
         verifyUserOrAdmin();

        Long userId = getCurrentUserId();

        Couple couple = getCoupleByUserIdOrThrow(userId);

        InvitationImage image = getImageById(request.imageId());

        Invitation invitation = Invitation.builder()
                .weddingDateTime(LocalDateTime.parse(request.weddingDateTime()))
                .place(request.place())
                .content(request.content())
                .imageUrl(image.getImageUrl())
                .build();

        Invitation savedInvitation = invitationRepository.save(invitation);

        coupleService.addInvitation(couple.getId(), savedInvitation.getId());

        return savedInvitation.getId();
    }

    private Long getCurrentUserId() {
        /**
         * TODO: 현재 로그인한 사용자의 ID를 가져오는 코드 구현 필요
         */
        return 1L;
    }

    private void verifyUserOrAdmin() {
        /**
         * TODO: 로그인 여부를 확인하는 코드 구현 필요
         * */
        return;
    }

    private Couple getCoupleByUserIdOrThrow(Long memberId) {
        return coupleService.getCoupleByUserId(memberId)
                .orElseThrow(() -> new IllegalArgumentException("커플을 찾을 수 없습니다."));
        //TODO: 커플을 찾을 수 없을 때 예외 처리 코드 구현 필요

    }

    private InvitationImage getImageById(Long imageId) {
        return invitationImageRepository.findById(imageId)
                .orElseThrow(() -> new IllegalArgumentException("이미지를 찾을 수 없습니다."));
        //TODO: 이미지를 찾을 수 없을 때 예외 처리 코드 구현 필요
    }


}
