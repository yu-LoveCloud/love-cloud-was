package com.lovecloud.payment.domain.repository;

import com.lovecloud.payment.domain.Payment;
import com.lovecloud.payment.exception.NotFoundPaymentException;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    boolean existsByImpUid(String impUid);

    Optional<Payment> findByImpUid(String impUid);

    default Payment findByIdOrThrow(Long id) {
        return findById(id).orElseThrow(NotFoundPaymentException::new);
    }

    default Payment findByImpUidOrThrow(String impUid) {
        return findByImpUid(impUid).orElseThrow(NotFoundPaymentException::new);
    }
}
