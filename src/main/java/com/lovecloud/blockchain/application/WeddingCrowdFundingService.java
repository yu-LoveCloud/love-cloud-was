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

    private final LCTokenService lcTokenService;

    @Value("${web3j.chain-id}")
    private BigInteger chainId;

    @Value("${web3j.funding-contract-address}")
    private String fundingContractAddress;

    @Value("${web3j.keyfile-password}")
    private String keyfilePassword;

    /**
     * 토큰 사용 승인 후 펀딩에 기여하는 메서드
     *
     * @param fundingId      기여할 펀딩 ID
     * @param amount         기여 금액
     * @param walletFilePath 사용자의 지갑 파일 경로
     * @return 트랜잭션 해시
     * @throws Exception 블록체인 연동 중 오류 발생 시 예외 처리
     */
    public String approveAndContribute(BigInteger fundingId, BigInteger amount, String walletFilePath) throws Exception {

        // 토큰 사용 승인
        String approvalTxHash = lcTokenService.approveTokens(walletFilePath, keyfilePassword, amount);

        // 승인 결과 검증
        if (approvalTxHash == null || approvalTxHash.isEmpty()) {
            throw new IllegalStateException("토큰 사용 승인에 실패하였습니다.");
        }

        // 펀딩 기여
        return contributeToFunding(fundingId, amount, walletFilePath);
    }

    /**
     * 펀딩 기여 메서드
     *
     * @param fundingId      기여할 펀딩 ID
     * @param amount         기여 금액
     * @param walletFilePath 사용자의 지갑 파일 경로
     * @return 트랜잭션 해시
     * @throws Exception 블록체인 연동 중 오류 발생 시 예외 처리
     */
    public String contributeToFunding(BigInteger fundingId, BigInteger amount, String walletFilePath) throws Exception {

        // 펀딩 스마트 계약 로드
        WeddingCrowdFunding fundingContract = loadContract(walletFilePath);

        // 펀딩 트랜잭션 전송
        TransactionReceipt receipt = fundingContract.contribute(fundingId, amount).send();

        // 트랜잭션 해시 반환
        return receipt.getTransactionHash();
    }

    /**
     * 블록체인에 펀딩을 생성하는 메서드
     *
     * @param funding        생성할 펀딩 도메인 객체
     * @param walletFilePath 사용자의 지갑 파일 경로
     * @return 트랜잭션 해시
     * @throws Exception 블록체인 연동 중 오류 발생 시 예외 처리
     */
    public String createCrowdfundingOnBlockchain(Funding funding, String walletFilePath) throws Exception {

        // 펀딩 스마트 계약 로드
        WeddingCrowdFunding fundingContract = loadContract(walletFilePath);

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
     * @param walletFilePath 사용자의 지갑 파일 경로
     * @return WeddingCrowdFunding 스마트 컨트랙트 인스턴스
     * @throws Exception 블록체인 연동 중 오류 발생 시 예외 처리
     */
    private WeddingCrowdFunding loadContract(String walletFilePath) throws Exception {

        // 사용자 지갑 정보 로드
        Credentials credentials = WalletUtils.loadCredentials(keyfilePassword, new File(walletFilePath));

        // 트랜잭션 매니저 생성 (chainId 포함)
        TransactionManager transactionManager = new RawTransactionManager(web3j, credentials, chainId.intValue());

        // 스마트 컨트랙트 로드
        return WeddingCrowdFunding.load(fundingContractAddress, web3j, transactionManager, new DefaultGasProvider());
    }
}
