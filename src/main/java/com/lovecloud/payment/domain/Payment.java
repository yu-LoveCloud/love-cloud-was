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

    @Column(name = "imp_uid", nullable = false, length = 100)
    private String impUid;

    @Column(name = "amount", nullable = false, length = 100)
    private Long amount;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "status", nullable = false, length = 100)
    private String status;

    @Column(name = "paid_at", nullable = false)
    private LocalDateTime paidAt;

    @Column(name = "pay_method", nullable = false, length = 100)
    private String payMethod;

    @Builder
    public Payment(String impUid, Long amount, String name, String status, LocalDateTime paidAt, String payMethod) {
        this.impUid = impUid;
        this.amount = amount;
        this.name = name;
        this.status = status;
        this.paidAt = paidAt;
        this.payMethod = payMethod;
    }
}
