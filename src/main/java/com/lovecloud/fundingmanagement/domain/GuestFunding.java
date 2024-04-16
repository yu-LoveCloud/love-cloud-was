package com.lovecloud.fundingmanagement.domain;

import com.lovecloud.global.domain.CommonRootEntity;
import com.lovecloud.payment.domain.Payment;
import com.lovecloud.usermanagement.domain.Guest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "guest_funding")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GuestFunding extends CommonRootEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "guest_funding_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guest_id", nullable = false)
    private Guest guest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "funding_id", nullable = false)
    private Funding funding;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment payment;

    @Column(name = "message", nullable = false, length = 300)
    private String message;

    @Builder
    public GuestFunding(Guest guest, Funding funding, Payment payment, String message) {
        this.guest = guest;
        this.funding = funding;
        this.payment = payment;
        this.message = message;
    }
}
