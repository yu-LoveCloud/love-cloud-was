package com.lovecloud.usermanagement.application;

import com.lovecloud.usermanagement.domain.Couple;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CoupleService {

    public Optional<Couple> getCoupleByUserId(Long memberId) {

        /**
         * 작성자: 염동환
         * TODO: WeddingUser Id로 커플을 찾아 반환하는 코드 구현 필요
         * */
        return Optional.empty();
    }

    public void addInvitation(Long coupleId, Long invitationId) {
        /**
         * 작성자: 염동환
         * TODO: 커플에 청첩장을 추가하는 코드 구현 필요
         * */
        return;
    }
}
