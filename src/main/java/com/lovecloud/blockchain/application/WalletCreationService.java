package com.lovecloud.blockchain.application;


import com.lovecloud.blockchain.domain.Wallet;
import com.lovecloud.blockchain.domain.repository.WalletRepository;
import com.lovecloud.blockchain.exception.FailCreateKeyPairException;
import com.lovecloud.blockchain.exception.FailCreateWalletException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.crypto.WalletUtils;

import java.io.File;

@Service
@RequiredArgsConstructor
public class WalletCreationService {

    private final String keyFilePassword = "${web3j.keyfile-password}"; //사용자 마다 달라야 하지만 일단 시스템에서 통일
    private final WalletRepository walletRepository;
    private final WalletVerifyService walletVerifyService;

    private Wallet wallet;

    /**
     * 지갑 생성 함수
     *
     * ECKeyPair를 생성한 후, 해당 키페어를 사용하여 지갑 파일을 생성
     * 지갑 파일은 지정된 디렉토리에 저장되며, 저장된 지갑 파일의 이름을 반환
     *
     * @return 생성된 지갑 파일의 이름 (예: UTC--2024-09-22T14-34-23.828000000Z--abcd1234)
     * @throws FailCreateKeyPairException 키 페어 생성에 실패한 경우 발생
     * @throws FailCreateWalletException 지갑 파일 생성에 실패한 경우 발생
     */
    public String creatWallet() {

        ECKeyPair ecKeyPair = null;

        try{
            ecKeyPair = Keys.createEcKeyPair();
        } catch (Exception e) {
            throw new FailCreateKeyPairException();
        }

        String walletDirectory = "./";

        try{
            return WalletUtils.generateWalletFile(keyFilePassword, ecKeyPair, new File(walletDirectory), true);
        } catch (Exception e) {
            throw new FailCreateWalletException();
        }

    }

    /**
     * 지갑 데이터베이스에 저장하는 함수
     *
     * 1. 지갑을 생성하여 지갑 파일을 만들고, Credentials 객체를 통해 지갑의 주소와 비밀 키 등을 추출
     * 2. 지갑 정보를 데이터베이스에 저장
     *
     * @return 저장된 Wallet 객체를 반환
     */
    public Wallet saveWallet(){

        String keyfile = creatWallet();
        Credentials account = walletVerifyService.verifyWallet(keyfile); //지갑의 Credentials 객체 (비밀 키, 공개 키, 주소 등의 정보를 포함)

        return walletRepository.save(Wallet.builder()
                .privateKey(account.getEcKeyPair().getPrivateKey().toString(16))
                .address(account.getAddress())
                .keyfile(keyfile)
                .keyfilePassword(keyFilePassword) //TODO: 시스템 공통이므로 DB에 저장 필요 여부 확인
                .build());
    }


}
