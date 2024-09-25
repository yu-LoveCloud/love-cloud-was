package com.lovecloud.blockchain.application;


import com.lovecloud.blockchain.exception.FailVerifyWalletException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;

@Service
@RequiredArgsConstructor
public class WalletVerifyService {


    private final String keyFilePassword = "${web3j.keyfile-password}"; //사용자 마다 달라야 하지만 일단 시스템에서 통일


    /**
     * 주어진 지갑 파일 이름을 통해 지갑을 검증하고, 해당 지갑의 Credentials(자격 증명)을 반환하는 함수
     *
     * 주어진 지갑 파일을 지정된 디렉토리에서 불러오고
     * 지갑 파일과 비밀번호를 사용해 지갑을 복호화하고 Credentials 객체를 생성
     * 생성된 Credentials는 지갑의 키 페어 및 주소 등의 정보를 포함
     *
     * @param walletFileName 지갑 파일의 이름 (예: UTC--2024-09-22T14-34-23.828000000Z--abcd1234)
     * @return 지갑의 Credentials 객체 (비밀 키, 공개 키, 주소 등의 정보를 포함)
     * @throws FailVerifyWalletException 지갑 파일 로드 및 검증에 실패한 경우 발생
     */
    public Credentials verifyWallet(String walletFileName) {
        String walletDirectory = "./";
        Credentials credentials = null;
        try {
            credentials = WalletUtils.loadCredentials(keyFilePassword, walletDirectory + walletFileName);
            return credentials;
        } catch (Exception e) {
            throw new FailVerifyWalletException();
        }
    }
}
