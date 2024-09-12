package com.lovecloud.blockchain.domain;

import com.lovecloud.global.domain.CommonRootEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "transfer")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Transfer extends CommonRootEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transfer_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_wallet_id", nullable = false)
    private Wallet fromWallet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_wallet_id", nullable = false)
    private Wallet toWallet;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Column(name = "transfer_date", nullable = false)
    private LocalDateTime transferDate;

    @Column(name = "from_balance", nullable = false)
    private Integer fromBalance;

    @Column(name = "to_balance", nullable = false)
    private Integer toBalance;
}


