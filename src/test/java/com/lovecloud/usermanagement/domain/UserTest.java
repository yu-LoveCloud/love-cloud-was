package com.lovecloud.usermanagement.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserTest {

    @Test
    @DisplayName("게스트 생성 테스트")
    void createGuestTest() {
        Guest guest = Guest.builder()
                .email("guest@example.com")
                .name("guest")
                .userRole(UserRole.GUEST)
                .phoneNumber("010-1234-5678")
                .password("password")
                .build();

        assertNotNull(guest);
        assertEquals("guest@example.com", guest.getEmail());
        assertEquals("guest", guest.getName());
        assertEquals(UserRole.GUEST, guest.getUserRole());
        assertEquals("010-1234-5678", guest.getPhoneNumber());
        assertEquals("password", guest.getPassword());
    }
}