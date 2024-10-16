package com.lovecloud.blockchain.application;

import com.lovecloud.blockchain.domain.LCToken;
import com.lovecloud.blockchain.exception.BlockchainException;
import com.lovecloud.infra.s3.KeyfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.DefaultGasProvider;

import java.io.File;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

@Service
@Transactional
@RequiredArgsConstructor
public class LCTokenService {

    private final Web3j web3j;

    @Value("${web3j.chain-id}")
    private BigInteger chainId;

    @Value("${web3j.token-contract-address}")
    private String tokenContractAddress;

    @Value("${web3j.keyfile-password}")
    private String keyfilePassword;

    @Value("${web3j.funding-contract-address}")
    private String fundingContractAddress;

    /**
     * LCToken 사용을 승인하는 메서드
     *
     * @param keyfileContent S3에 저장된 지갑 파일 내용
     * @param amount 승인할 토큰 수량
     * @return 승인 트랜잭션 해시
     */
    public String approveTokens(String keyfileContent, BigInteger amount) {
        try {
            // 사용자 지갑 정보를 가져옴
            Credentials credentials = WalletUtils.loadJsonCredentials(keyfilePassword, keyfileContent);

            // 트랜잭션 매니저 생성 (chainId 포함)
            TransactionManager transactionManager = new RawTransactionManager(web3j, credentials, chainId.intValue());

            // LCToken 스마트 계약 로드
            LCToken tokenContract = LCToken.load(tokenContractAddress, web3j, transactionManager, new DefaultGasProvider());

            // 승인 트랜잭션 전송
            TransactionReceipt receipt = tokenContract.approve(fundingContractAddress, amount).send();

            // 트랜잭션 해시 반환
            return receipt.getTransactionHash();
        } catch (Exception e) {
            throw new BlockchainException("블록체인 토큰 승인 중 오류가 발생했습니다.", e);
        }
    }
}
