package com.lovecloud.blockchain.domain;

import com.lovecloud.global.domain.CommonRootEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
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

    @Column(name = "private_key", nullable = false)
    private String privateKey; //지갑 비밀키

    @Column(name = "address", nullable = false)
    private String address; //지갑 공개 주소

    @Column(name = "keyfile", nullable = false)
    private String keyfile; //지갑 키 파일 (지갑의 비밀키가 암호화 되어있음)

    @Column(name = "keyfile_password", nullable = false) //TODO: 시스템 통일이므로 삭제 가능
    private String keyfilePassword; //지갑 키 복호화 비밀번호



    @Builder
    public Wallet(String address, String keyfile, String keyfilePassword, String privateKey) {
        this.privateKey =  privateKey;
        this.address = address;
        this.keyfile = keyfile;
        this.keyfilePassword = keyfilePassword;
    }

}
