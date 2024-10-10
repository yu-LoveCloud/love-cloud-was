package com.lovecloud.blockchain.application;

import com.lovecloud.blockchain.exception.FailTransferEtherException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.math.BigDecimal;
import java.math.BigInteger;

@Slf4j
@Service
@RequiredArgsConstructor
public class EtherTransferService {

    @Value("${web3j.chain-id}")
    private int chainId;

    private final Web3j web3j;
    private final Credentials adminCredentials;

    public String transferEther(String toAddress) {

        try {
            BigInteger nonce = web3j.ethGetTransactionCount(adminCredentials.getAddress(), DefaultBlockParameterName.PENDING).send().getTransactionCount();
            BigInteger gasPrice = web3j.ethGasPrice().send().getGasPrice().multiply(BigInteger.valueOf(2)); // 네트워크 가스 가격의 2배로 설정
            BigInteger gasLimit = BigInteger.valueOf(300000L);
            BigInteger value = Convert.toWei(BigDecimal.valueOf(0.05), Convert.Unit.ETHER).toBigInteger();

            RawTransaction rawTransaction = RawTransaction.createEtherTransaction(nonce, gasPrice, gasLimit, toAddress, value);

            byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, chainId, adminCredentials);
            String hexValue = Numeric.toHexString(signedMessage);

            EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).send();
            if (ethSendTransaction.hasError()) {
                log.error("이더 전송 중 오류 발생: {}", ethSendTransaction.getError().getMessage());
            } else {
                log.info("이더 전송 성공: {}", ethSendTransaction.getTransactionHash());
            }

            return ethSendTransaction.getTransactionHash();
        } catch (Exception e) {
            throw new FailTransferEtherException();
        }
    }
}
