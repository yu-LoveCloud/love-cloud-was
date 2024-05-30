package com.lovecloud.global.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * UUID를 생성하고 이를 8자리로 줄이는 유틸리티 클래스입니다.
 */
public class UUIDUtil {

    /**
     * 8자리의 짧은 UUID를 생성하는 메서드입니다.
     *
     * @return 8자리의 짧은 UUID 문자열
     */
    public static String generateShortUUID() {
        String uuidString = UUID.randomUUID().toString();
        byte[] uuidBytes = uuidString.getBytes(StandardCharsets.UTF_8);
        byte[] hashBytes = hashUUID(uuidBytes);

        return convertToHex(hashBytes);
    }

    /**
     * 주어진 바이트 배열을 SHA-256 해시 함수로 해싱하여 바이트 배열로 반환합니다.
     *
     * @param uuidBytes 해싱할 UUID의 바이트 배열
     * @return 해싱된 바이트 배열
     * @throws RuntimeException SHA-256 알고리즘을 사용할 수 없는 경우
     */
    private static byte[] hashUUID(byte[] uuidBytes) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            return messageDigest.digest(uuidBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 알고리즘을 찾을 수 없습니다.", e);
        }
    }

    /**
     * 해시된 바이트 배열의 앞 4바이트를 2자리의 16진수 문자열로 변환하여 8자리의 고유 값을 생성합니다.
     *
     * @param hashBytes 해시된 바이트 배열
     * @return 8자리의 16진수 문자열
     */
    private static String convertToHex(byte[] hashBytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            sb.append(String.format("%02x", hashBytes[i]));
        }
        return sb.toString();
    }
}
