package com.lovecloud.payment.domain;

import com.lovecloud.fundingmanagement.domain.Funding;
import com.lovecloud.global.domain.CommonRootEntity;
import com.lovecloud.usermanagement.domain.Guest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "payment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment extends CommonRootEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long id;

    //아임포트 고유번호
    @Column(name = "imp_uid", nullable = false, length = 100)
    private String impUid;

    //주문번호
    @Column(name = "merchant_uid", nullable = false, length = 100)
    private String merchantUid;

    @Column(name = "amount", nullable = false, length = 100)
    private Long amount;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false, length = 100)
    private PaymentStatus paymentStatus;

    @Column(name = "paid_at", nullable = false)
    private LocalDateTime paidAt;

    @Column(name = "pay_method", nullable = false, length = 100)
    private String payMethod;

    @Builder
    public Payment(String impUid, String merchantUid, Long amount, String name, PaymentStatus paymentStatus, LocalDateTime paidAt, String payMethod) {
        this.impUid = impUid;
        this.merchantUid = merchantUid;
        this.amount = amount;
        this.name = name;
        this.paymentStatus = paymentStatus;
        this.paidAt = paidAt;
        this.payMethod = payMethod;
    }

    public void cancel() {
        this.paymentStatus = PaymentStatus.CANCELED;
    }
}
