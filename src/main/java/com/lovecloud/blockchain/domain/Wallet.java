package com.lovecloud.blockchain.domain;

import com.lovecloud.global.domain.CommonRootEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "wallet")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Wallet extends CommonRootEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wallet_id")
    private Long id;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "keyfile", nullable = false)
    private String keyfile;

    @Column(name = "keyfile_password", nullable = false)
    private String keyfilePassword;
}
