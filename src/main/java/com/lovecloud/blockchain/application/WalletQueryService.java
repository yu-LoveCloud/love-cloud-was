package com.lovecloud.blockchain.application;

import com.lovecloud.auth.domain.GuestRepository;
import com.lovecloud.blockchain.domain.repository.WalletRepository;
import com.lovecloud.usermanagement.domain.Couple;
import com.lovecloud.usermanagement.domain.Guest;
import com.lovecloud.usermanagement.domain.repository.CoupleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class WalletQueryService {

    private final GuestRepository guestRepository;
    private final CoupleRepository coupleRepository;

    public String findWalletAddressByGuestId(Long guestId) { //하객 id로 wallet 주소 조회

        Guest guest = guestRepository.findByIdOrThrow(guestId);

        return guest.getWallet().getAddress();
    }

    public String findWalletAddressByCoupleId(Long coupleId) { //커플 id로 wallet 주소 조회

        Couple couple = coupleRepository.findByIdOrThrow(coupleId);

        return couple.getWallet().getAddress();
    }
}
