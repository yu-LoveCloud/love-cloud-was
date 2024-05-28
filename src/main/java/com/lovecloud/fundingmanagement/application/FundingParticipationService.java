package com.lovecloud.fundingmanagement.application;

import com.lovecloud.fundingmanagement.application.command.ParticipateFundingCommand;
import com.lovecloud.fundingmanagement.domain.Funding;
import com.lovecloud.fundingmanagement.domain.GuestFunding;
import com.lovecloud.fundingmanagement.domain.repository.FundingRepository;
import com.lovecloud.fundingmanagement.domain.repository.GuestFundingRepository;
import com.lovecloud.usermanagement.domain.Guest;
import com.lovecloud.usermanagement.domain.repository.GuestRepository;
import com.lovecloud.usermanagement.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class FundingParticipationService {

    private final GuestRepository guestRepository;
    private final FundingRepository fundingRepository;
    private final GuestFundingRepository guestFundingRepository;

    public void participateInFunding(ParticipateFundingCommand command) {
        Guest guest = guestRepository.findByIdOrThrow(command.memberId());
        Funding funding = fundingRepository.findByIdOrThrow(command.fundingId());
        GuestFunding guestFunding = command.toGuestFunding(guest, funding);
        guestFundingRepository.save(guestFunding);
    }
}
