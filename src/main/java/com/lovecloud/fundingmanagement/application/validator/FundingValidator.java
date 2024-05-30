package com.lovecloud.fundingmanagement.application.validator;

import com.lovecloud.fundingmanagement.domain.Funding;
import com.lovecloud.fundingmanagement.exception.FundingTargetExceededException;
import com.lovecloud.payment.domain.Payment;
import com.lovecloud.payment.domain.PaymentStatus;
import com.lovecloud.payment.exception.InvalidPaymentStatusException;
import org.springframework.stereotype.Component;

@Component
public class FundingValidator {

    public void validatePaymentStatus(Payment payment) {
        if (payment.getPaymentStatus() != PaymentStatus.PAID) {
            throw new InvalidPaymentStatusException();
        }
    }

    public void validateFundingAmount(Funding funding, Long amount) {
        if (funding.getCurrentAmount() + amount > funding.getTargetAmount()) {
            throw new FundingTargetExceededException();
        }
    }
}
