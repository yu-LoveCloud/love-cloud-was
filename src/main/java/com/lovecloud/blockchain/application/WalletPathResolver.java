package com.lovecloud.blockchain.application;

public class WalletPathResolver {

    private static final String PREFIX = "./";

    private WalletPathResolver() {
        throw new AssertionError("인스턴스화 할 수 없습니다.");
    }

    public static String resolveWalletPath(String keyfile) {
        if (!keyfile.startsWith(PREFIX)) {
            return PREFIX + keyfile;
        }
        return keyfile;
    }
}
