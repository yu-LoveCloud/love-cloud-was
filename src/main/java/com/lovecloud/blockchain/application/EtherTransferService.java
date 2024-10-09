package com.lovecloud.blockchain.application;

import com.lovecloud.blockchain.exception.FailTransferEtherException;
import lombok.RequiredArgsConstructor;
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

@Service
@RequiredArgsConstructor
public class EtherTransferService {

    @Value("${web3j.chain-id}")
    private int chainId;

    private final Web3j web3j;
    private final Credentials adminCredentials;

    public String transferEther(String toAddress) {

        try {
            BigInteger nonce = web3j.ethGetTransactionCount(adminCredentials.getAddress(), DefaultBlockParameterName.LATEST).send().getTransactionCount();
            BigInteger gasPrice = BigInteger.valueOf(1000000000L); //TODO: gasPrice, gasLimit, value 설정 추후 협의 필
            BigInteger gasLimit = BigInteger.valueOf(300000L);
            BigInteger value = Convert.toWei(BigDecimal.valueOf(0.01), Convert.Unit.ETHER).toBigInteger();

            RawTransaction rawTransaction = RawTransaction.createEtherTransaction(nonce, gasPrice, gasLimit, toAddress, value);

            byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, chainId, adminCredentials);
            String hexValue = Numeric.toHexString(signedMessage);

            EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).send();

            return ethSendTransaction.getTransactionHash();
        } catch (Exception e) {
            throw new FailTransferEtherException();
        }
    }
}
