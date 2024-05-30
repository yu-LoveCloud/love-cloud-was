package com.lovecloud.fundingmanagement.domain;

import com.lovecloud.global.domain.CommonRootEntity;
import com.lovecloud.productmanagement.domain.ProductOptions;
import com.lovecloud.usermanagement.domain.Couple;
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
import jakarta.persistence.Table;
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

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "message", nullable = false, length = 300)
    private String message;

    @Column(name = "target_amount", nullable = false)
    private Integer targetAmount;

    @Column(name = "current_amount", nullable = false)
    private Integer currentAmount = 0;

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
    public Funding(String title, String message, Integer targetAmount, LocalDateTime endDate,
            ProductOptions productOptions, Couple couple) {
        this.title = title;
        this.message = message;
        this.targetAmount = targetAmount;
        this.endDate = endDate;
        this.productOptions = productOptions;
        this.couple = couple;
    }
}
