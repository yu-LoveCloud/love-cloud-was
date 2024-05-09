package com.lovecloud.invitationmanagement.application;

import com.lovecloud.invitationmanagement.domain.Invitation;
import com.lovecloud.invitationmanagement.domain.InvitationImage;
import com.lovecloud.invitationmanagement.presentation.request.CreateInvitationRequest;
import com.lovecloud.invitationmanagement.repository.InvitationImageRepository;
import com.lovecloud.invitationmanagement.repository.InvitationRepository;
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
//    private final CoupleService coupleService;

    public Long addInvitation(final Long memberId, final CreateInvitationRequest request) {
        Couple couple = getCoupleByUserIdOrThrow(memberId);

        InvitationImage image = getImageById(request.imageId());

        Invitation invitation = Invitation.builder()
                .weddingDateTime(LocalDateTime.parse(request.weddingDateTime()))
                .place(request.place())
                .content(request.content())
                .imageUrl(image.getImageUrl())
                .build();

        invitationRepository.save(invitation);

        //커플 객체에 청첩장 객체를 등록
//        coupleService.addInvitation(couple.getId(), invitation.getId());

        return invitation.getId();
    }

    private Couple getCoupleByUserIdOrThrow(Long memberId) {
//        coupleService.getCoupleByUserId(memberId)
//                .orElseThrow(() -> new IllegalArgumentException("커플을 찾을 수 없습니다."));

        return null;
    }

    private InvitationImage getImageById(Long imageId) {
        return invitationImageRepository.findById(imageId)
                .orElseThrow(() -> new IllegalArgumentException("이미지를 찾을 수 없습니다."));
    }


}
