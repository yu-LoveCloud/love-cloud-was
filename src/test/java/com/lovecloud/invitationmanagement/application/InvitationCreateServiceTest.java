package com.lovecloud.invitationmanagement.application;

import com.lovecloud.invitationmanagement.domain.Invitation;
import com.lovecloud.invitationmanagement.domain.InvitationImage;
import com.lovecloud.invitationmanagement.presentation.request.CreateInvitationRequest;
import com.lovecloud.invitationmanagement.repository.InvitationImageRepository;
import com.lovecloud.invitationmanagement.repository.InvitationRepository;
import com.lovecloud.usermanagement.application.CoupleService;
import com.lovecloud.usermanagement.domain.*;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class InvitationCreateServiceTest {

    @MockBean
    private InvitationRepository invitationRepository;

    @MockBean
    private InvitationImageRepository invitationImageRepository;

    @MockBean
    private CoupleService coupleService;

    @Autowired
    private InvitationCreateService invitationCreateService;

    @Test
    public void testAddInvitationShouldReturnValidId() {
        // Given
        Long memberId = 1L;
        CreateInvitationRequest request = new CreateInvitationRequest(
                1L, "2022-12-31T23:59:59", "서울시 강남구", "내용"
        );

        InvitationImage image = InvitationImage.builder()
                .imageUrl("http://image.com")
                .build();

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

        when(coupleService.getCoupleByUserId(memberId)).thenReturn(Optional.of(couple));
        when(invitationImageRepository.findById(request.imageId())).thenReturn(Optional.of(image));

        when(invitationRepository.save(any(Invitation.class))).thenAnswer(new Answer<Invitation>() {
            @Override
            public Invitation answer(InvocationOnMock invocation) throws Throwable {
                Invitation savedInvitation = invocation.getArgument(0);
                ReflectionTestUtils.setField(savedInvitation, "id", 1L); // ID 설정
                return savedInvitation; // 수정된 객체 반환
            }
        });

        // When
        Long invitationId = invitationCreateService.addInvitation(request);

        // Then
        assertNotNull(invitationId, "The returned invitation ID should not be null");
        verify(invitationRepository).save(any(Invitation.class));


    }
}