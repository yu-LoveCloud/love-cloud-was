package com.lovecloud.payment.domain.repository;

import com.lovecloud.payment.domain.Payment;
import com.lovecloud.payment.exception.NotFoundPaymentException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    default Payment findByIdOrThrow(Long id) {
        return findById(id).orElseThrow(NotFoundPaymentException::new);
    }
}
