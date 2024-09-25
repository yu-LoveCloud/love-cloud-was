package com.lovecloud.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

@Configuration
public class Web3jConfig {

    @Bean
    public Web3j web3j() {
        return Web3j.build(new HttpService("${web3j.private-network}"));
    }
}
