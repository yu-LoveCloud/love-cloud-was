package com.lovecloud.usermanagement.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

    @Test
    @DisplayName("웨딩 유저 생성 테스트")
    void createWeddingUserTest() {
        WeddingUser weddingUser = WeddingUser.builder()
                .email("wedding@example.com")
                .name("wedding")
                .userRole(UserRole.WEDDING_USER)
                .phoneNumber("010-1234-5678")
                .password("password")
                .accountStatus(AccountStatus.ACTIVE)
                .weddingRole(WeddingRole.BRIDE)
                .oauthId("oauthId")
                .oauth("oauth")
                .invitationCode("invitationCode")
                .build();

        assertNotNull(weddingUser);
        assertEquals("wedding@example.com", weddingUser.getEmail());
        assertEquals("wedding", weddingUser.getName());
        assertEquals(UserRole.WEDDING_USER, weddingUser.getUserRole());
        assertEquals("010-1234-5678", weddingUser.getPhoneNumber());
        assertEquals("password", weddingUser.getPassword());
        assertEquals(AccountStatus.ACTIVE, weddingUser.getAccountStatus());
        assertEquals(WeddingRole.BRIDE, weddingUser.getWeddingRole());
        assertEquals("oauthId", weddingUser.getOauthId());
        assertEquals("oauth", weddingUser.getOauth());
        assertEquals("invitationCode", weddingUser.getInvitationCode());
    }
}