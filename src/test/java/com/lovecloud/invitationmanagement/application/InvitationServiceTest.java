package com.lovecloud.invitationmanagement.application;

import com.lovecloud.invitationmanagement.domain.Invitation;
import com.lovecloud.invitationmanagement.domain.InvitationImage;
import com.lovecloud.invitationmanagement.presentation.request.CreateInvitationRequest;
import com.lovecloud.invitationmanagement.repository.InvitationImageRepository;
import com.lovecloud.invitationmanagement.repository.InvitationRepository;
import com.lovecloud.usermanagement.application.CoupleService;
import com.lovecloud.usermanagement.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class InvitationServiceTest {
    @Mock
    private InvitationRepository invitationRepository;
    @Mock
    private InvitationImageRepository invitationImageRepository;
    @Mock
    private CoupleService coupleService;

    @InjectMocks
    private InvitationService invitationService;

    @BeforeEach
    void setup() {
        invitationService = new InvitationService(invitationRepository, invitationImageRepository, coupleService);
    }

    @Test
    public void testAddInvitation() {
        // Given
        Long memberId = 1L;
        CreateInvitationRequest request = new CreateInvitationRequest(
                1L,
                "2022-12-31T23:59:59",
                "서울시 강남구",
                "내용"

        );
        InvitationImage image = InvitationImage.builder()
                        .imageUrl("http://image.com")
                        .build();

        when(invitationImageRepository.findById(request.imageId())).thenReturn(Optional.of(image));

        WeddingUser groom = WeddingUser.builder()
                        .email("22@aa.com")
                        .name("groom")
                        .userRole(UserRole.WEDDING_USER)
                        .phoneNumber("010-1234-5678")
                        .password("1234")
                        .accountStatus(AccountStatus.ACTIVE)
                        .weddingRole(WeddingRole.GROOM)
                        .build();

        WeddingUser bride = WeddingUser.builder()
                        .email("he@he.com")
                        .name("bride")
                        .userRole(UserRole.WEDDING_USER)
                        .phoneNumber("010-1234-5678")
                        .password("1234")
                        .accountStatus(AccountStatus.ACTIVE)
                        .weddingRole(WeddingRole.BRIDE)
                        .build();

        Couple couple = Couple.builder()
                        .groom(groom)
                        .bride(bride)
                        .refundAccount("1234")
                        .refundBankName("국민")
                        .invitation(null)
                        .build();

        Invitation invitation = Invitation.builder()
                        .weddingDateTime(LocalDateTime.parse(request.weddingDateTime()))
                        .place(request.place())
                        .content(request.content())
                        .imageUrl(image.getImageUrl())
                        .build();

        ReflectionTestUtils.setField(invitation, "id", 1L);

        when(coupleService.getCoupleByUserId(memberId)).thenReturn(Optional.of(couple));
        when(invitationImageRepository.findById(request.imageId())).thenReturn(Optional.of(image));
        when(invitationRepository.save(any(Invitation.class))).thenReturn(invitation);


        // When
        Long invitationId = invitationService.addInvitation(request);

        // Then
        assertNotNull(invitationId);
        assertEquals(1L, invitationId);
        verify(invitationRepository).save(any(Invitation.class));
    }

}