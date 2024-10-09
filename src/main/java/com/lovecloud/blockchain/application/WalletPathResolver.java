package com.lovecloud.blockchain.application;

import org.springframework.stereotype.Service;

@Service
public class WalletPathResolver {

    public String resolveWalletPath(String keyfile) {
        if (!keyfile.startsWith("./")) {
            return "./" + keyfile;
        }
        return keyfile;
    }
}
