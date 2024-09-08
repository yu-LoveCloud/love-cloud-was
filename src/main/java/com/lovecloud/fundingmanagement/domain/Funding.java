package com.lovecloud.fundingmanagement.domain;

import com.lovecloud.blockchain.domain.Wallet;
import com.lovecloud.global.domain.CommonRootEntity;
import com.lovecloud.productmanagement.domain.ProductOptions;
import com.lovecloud.usermanagement.domain.Couple;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "funding")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Funding extends CommonRootEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "funding_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "message", nullable = false, length = 300)
    private String message;

    @Column(name = "target_amount", nullable = false)
    private Long targetAmount;

    @Column(name = "current_amount", nullable = false)
    private Long currentAmount = 0L;

    @Enumerated(EnumType.STRING)
    @Column(name = "funding_status", nullable = false)
    private FundingStatus status = FundingStatus.IN_PROGRESS;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_options_id", nullable = false)
    private ProductOptions productOptions;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "couple_id", nullable = false)
    private Couple couple;

    @Builder
    public Funding(String title, String message, Long targetAmount, LocalDateTime endDate,
            ProductOptions productOptions, Couple couple) {
        this.title = title;
        this.message = message;
        this.targetAmount = targetAmount;
        this.endDate = endDate;
        this.productOptions = productOptions;
        this.couple = couple;
    }

    public void increaseCurrentAmount(Long amount) {
        this.currentAmount += amount;
        if (this.currentAmount >= this.targetAmount && this.status == FundingStatus.IN_PROGRESS) {
            this.status = FundingStatus.COMPLETED;
            registerEvent(new FundingCompletedEvent(this.id));
        }
    }
}
