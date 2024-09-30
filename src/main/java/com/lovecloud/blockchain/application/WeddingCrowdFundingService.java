package com.lovecloud.blockchain.application;

import com.lovecloud.blockchain.domain.LCToken;
import com.lovecloud.blockchain.domain.WeddingCrowdFunding;
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

@Service
@Transactional
@RequiredArgsConstructor
public class WeddingCrowdFundingService {
    private final Web3j web3j;
    @Value("${web3j.chain-id}")
    private BigInteger chainId;
    @Value("${web3j.funding-contract-address}")
    private String fundingContractAddress;
    @Value("${web3j.keyfile-password}")
    private String password;


    //펀딩 기여 메서드
    public String contributeToFunding(String walletFilePath, BigInteger fundingId, BigInteger amount) throws Exception {

        // 사용자 지갑 정보를 가져옴
        Credentials credentials = WalletUtils.loadCredentials(password, new File(walletFilePath));

        // 트랜잭션 매니저 생성 (chainId 포함)
        TransactionManager transactionManager = new RawTransactionManager(web3j, credentials, chainId.intValue());

        // 펀딩 스마트 계약 로드
        WeddingCrowdFunding fundingContract = WeddingCrowdFunding.load(fundingContractAddress, web3j, transactionManager, new DefaultGasProvider());

        // 펀딩 트랜잭션 전송
        TransactionReceipt receipt = fundingContract.contribute(fundingId, amount).send();

        // 트랜잭션 해시 반환
        return receipt.getTransactionHash();
    }
}
