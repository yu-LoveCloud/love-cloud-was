package com.lovecloud.payment.domain;

import com.lovecloud.global.domain.CommonRootEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "payment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment extends CommonRootEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long id;

    @Column(name = "payment_number", nullable = false, length = 100)
    private String paymentNumber;

    @Column(name = "payment_method", nullable = false, length = 100)
    private String paymentMethod;

    @Column(name = "payment_info", nullable = false, length = 100)
    private String paymentInfo;

    @Column(name = "payment_status", nullable = false, length = 100)
    private String paymentStatus;

    @Builder
    public Payment(String paymentNumber, String paymentMethod, String paymentInfo,
            String paymentStatus) {
        this.paymentNumber = paymentNumber;
        this.paymentMethod = paymentMethod;
        this.paymentInfo = paymentInfo;
        this.paymentStatus = paymentStatus;
    }
}
