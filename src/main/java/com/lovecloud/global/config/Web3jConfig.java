package com.lovecloud.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.crypto.exception.CipherException;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;

@Configuration
public class Web3jConfig {

    @Bean
    public Web3j web3j() {
        return Web3j.build(new HttpService("${web3j.private-network}"));
    }

    @Bean
    public Credentials adminCredentials() throws IOException, CipherException {
        return WalletUtils.loadCredentials("${web3j.admin-wallet-password}", "${web3j.admin-keyfile-path}");
    }
}
