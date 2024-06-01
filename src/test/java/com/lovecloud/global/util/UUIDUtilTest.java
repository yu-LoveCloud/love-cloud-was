package com.lovecloud.global.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UUIDUtilTest {

    @Test
    void testGenerateShortUUID() {
        for (int i = 0; i < 10; i++) {
            String shortUUID = UUIDUtil.generateShortUUID();
            System.out.println("8자리 UUID 생성: " + shortUUID);
            assertNotNull(shortUUID);
            assertEquals(8, shortUUID.length());
            assertTrue(shortUUID.matches("[0-9a-f]{8}"));
        }
    }
}