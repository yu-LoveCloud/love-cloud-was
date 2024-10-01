package com.lovecloud.blockchain.application;

import com.lovecloud.blockchain.domain.WeddingCrowdFunding;
import com.lovecloud.fundingmanagement.domain.Funding;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.DefaultGasProvider;

import java.io.File;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
@Transactional
@RequiredArgsConstructor
public class WeddingCrowdFundingService {

    private Web3j web3j;

    @Value("${web3j.private-network}")
    private String privateNetworkUrl;

    @Value("${web3j.chain-id}")
    private BigInteger chainId;

    @Value("${web3j.funding-contract-address}")
    private String fundingContractAddress;

    @Value("${web3j.keyfile-password}")
    private String keyfilePassword;

    @Value("${web3j.wallet.file-path}")
    private String walletFilePath;

    @PostConstruct
    public void init() {
        this.web3j = Web3j.build(new HttpService(privateNetworkUrl));
    }

    /**
     * 펀딩 기여 메서드
     *
     * @param fundingId 기여할 펀딩 ID
     * @param amount 기여 금액
     * @return 트랜잭션 해시
     * @throws Exception 블록체인 연동 중 오류 발생 시 예외 처리
     */
    public String contributeToFunding(BigInteger fundingId, BigInteger amount) throws Exception {

        // 펀딩 스마트 계약 로드
        WeddingCrowdFunding fundingContract = loadContract();

        // 펀딩 트랜잭션 전송
        TransactionReceipt receipt = fundingContract.contribute(fundingId, amount).send();

        // 트랜잭션 해시 반환
        return receipt.getTransactionHash();
    }

    /**
     * 블록체인에 펀딩을 생성하는 메서드
     *
     * @param funding 생성할 펀딩 도메인 객체
     * @return 트랜잭션 해시
     * @throws Exception 블록체인 연동 중 오류 발생 시 예외 처리
     */
    public String createCrowdfundingOnBlockchain(Funding funding) throws Exception {

        // 펀딩 스마트 계약 로드
        WeddingCrowdFunding fundingContract = loadContract();

        BigInteger goal = BigInteger.valueOf(funding.getTargetAmount());

        // 현재 시간과 마감 시간의 차이를 게산하여 duration으로 변환
        LocalDateTime now = LocalDateTime.now();
        BigInteger duration = BigInteger.valueOf(funding.getEndDate().toEpochSecond(ZoneOffset.UTC) - now.toEpochSecond(ZoneOffset.UTC));

        // duration이 음수가 되지 않도록 체크
        if (duration.compareTo(BigInteger.ZERO) < 0) {
            throw new IllegalArgumentException("마감 시간이 현재 시간보다 이전입니다.");
        }

        // 펀딩 트랜잭션 전송
        TransactionReceipt receipt = fundingContract.createCrowdfunding(goal, duration).send();

        // 트랜잭션 해시 반환
        return receipt.getTransactionHash();
    }

    /**
     * 지갑 정보를 로드하고 트랜잭션 매니저를 생성한 후, 스마트 컨트랙트를 로드하는 메서드
     *
     * @return WeddingCrowdFunding 스마트 컨트랙트 인스턴스
     * @throws Exception 블록체인 연동 중 오류 발생 시 예외 처리
     */
    private WeddingCrowdFunding loadContract() throws Exception {

        // 사용자 지갑 정보 로드
        Credentials credentials = WalletUtils.loadCredentials(keyfilePassword, new File(walletFilePath));

        // 트랜잭션 매니저 생성 (chainId 포함)
        TransactionManager transactionManager = new RawTransactionManager(web3j, credentials, chainId.intValue());

        // 스마트 컨트랙트 로드
        return WeddingCrowdFunding.load(fundingContractAddress, web3j, transactionManager, new DefaultGasProvider());
    }
}
