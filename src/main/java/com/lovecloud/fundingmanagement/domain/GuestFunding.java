package com.lovecloud.fundingmanagement.domain;

import com.lovecloud.global.domain.CommonRootEntity;
import com.lovecloud.payment.domain.Payment;
import com.lovecloud.usermanagement.domain.Guest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "phone_number", nullable = false, length = 20)
    private String phoneNumber;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "funding_amount", nullable = false)
    private Long fundingAmount;

    @Column(name = "message", nullable = false, length = 300)
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(name = "participation_status", nullable = false)
    private ParticipationStatus participationStatus = ParticipationStatus.PENDING;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guest_id", nullable = false)
    private Guest guest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "funding_id", nullable = false)
    private Funding funding;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @Builder
    public GuestFunding(String name, String phoneNumber, String email, Long fundingAmount,
            String message, Guest guest, Funding funding) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.fundingAmount = fundingAmount;
        this.message = message;
        this.guest = guest;
        this.funding = funding;
    }
}
