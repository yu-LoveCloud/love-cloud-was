package com.lovecloud.fundingmanagement.domain;

import com.lovecloud.global.domain.DomainEvent;

public record FundingCompletedEvent(
        Long fundingId
) implements DomainEvent<Long> {

        @Override
        public Long id() {
            return fundingId();
        }
}
