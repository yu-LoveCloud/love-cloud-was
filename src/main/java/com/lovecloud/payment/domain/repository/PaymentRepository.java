package com.lovecloud.payment.domain.repository;

import com.lovecloud.payment.domain.Payment;
import com.lovecloud.payment.exception.NotFoundPaymentException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    boolean existsByImpUid(String impUid);

    Optional<Payment> findByImpUid(String impUid);

    default Payment findByImpUidOrThrow(String impUid) {
        return findByImpUid(impUid).orElseThrow(NotFoundPaymentException::new);
    }
}
