package com.lovecloud.invitationmanagement.application;

import com.lovecloud.auth.domain.Password;
import com.lovecloud.auth.domain.WeddingUserRepository;
import com.lovecloud.invitationmanagement.domain.Invitation;
import com.lovecloud.invitationmanagement.domain.InvitationImage;
import com.lovecloud.invitationmanagement.domain.repository.InvitationImageRepository;
import com.lovecloud.invitationmanagement.domain.repository.InvitationRepository;
import com.lovecloud.invitationmanagement.query.response.InvitationDetailResponse;
import com.lovecloud.usermanagement.domain.*;
import com.lovecloud.usermanagement.domain.repository.CoupleRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@Tag("InvitationQueryServiceTest")
@DisplayName("초대장 조회 서비스 (InvitationQueryService) 은(는)")
class InvitationQueryServiceTest {

    Invitation invitation;
    Couple couple;

    @Autowired
    private InvitationQueryService invitationQueryService;

    @Autowired
    private InvitationRepository invitationRepository;

    @Autowired
    private CoupleRepository coupleRepository;

    @Autowired
    private WeddingUserRepository weddingUserRepository;

    @Autowired
    private InvitationImageRepository invitationImageRepository;

    @BeforeEach
    void setUp() {
        // 신랑 객체 생성
        WeddingUser groom = WeddingUser.builder()
                .email("groom@example.com")
                .name("John Doe")
                .userRole(UserRole.WEDDING_USER) // UserRole은 enum 또는 적절한 값으로 설정
                .phoneNumber("010-1234-5678")
                .password(new Password("12345")) // 보통 해시된 비밀번호를 사용
                .accountStatus(AccountStatus.ACTIVE) // AccountStatus는 enum 또는 적절한 값으로 설정
                .weddingRole(WeddingRole.GROOM) // WeddingRole은 enum 또는 적절한 값으로 설정
                .oauthId(null) // OAuth ID가 없으면 null
                .oauth(null) // OAuth 제공자가 없으면 null
                .invitationCode("INV123GROOM") // 임의의 초대 코드
                .build();
        groom = weddingUserRepository.save(groom);

        // 신부 객체 생성
        WeddingUser bride = WeddingUser.builder()
                .email("bride@example.com")
                .name("Jane Smith")
                .userRole(UserRole.WEDDING_USER) // UserRole은 enum 또는 적절한 값으로 설정
                .phoneNumber("010-8765-4321")
                .password(new Password("12345")) // 보통 해시된 비밀번호를 사용
                .accountStatus(AccountStatus.ACTIVE) // AccountStatus는 enum 또는 적절한 값으로 설정
                .weddingRole(WeddingRole.BRIDE) // WeddingRole은 enum 또는 적절한 값으로 설정
                .oauthId(null) // OAuth ID가 없으면 null
                .oauth(null) // OAuth 제공자가 없으면 null
                .invitationCode("INV123BRIDE") // 임의의 초대 코드
                .build();
        bride = weddingUserRepository.save(bride);

        // 부부 객체 생성
        couple = Couple.builder()
                .groom(groom)
                .bride(bride)
                .build();
        couple = coupleRepository.save(couple);

        //invitationImage 객체 생성
        InvitationImage invitationImage = InvitationImage.builder()
                .imageName("image")
                .build();
        invitationImage = invitationImageRepository.save(invitationImage);

        //초대장 객체 생성
        Invitation invitation = Invitation.builder()
                .invitationImage(invitationImage)
                .weddingDateTime(LocalDateTime.parse("2022-10-10 10:10:10"))
                .place("Seoul")
                .content("Welcome to our wedding!")
                .build();
        invitation = invitationRepository.save(invitation);

        couple.setInvitation(invitation);

    }

    @Nested
    class findById {
        @Test
        void 청첩장_조회_성공시() {
            // given
            Long invitationId = invitation.getId();
            // when
            InvitationDetailResponse invitationDetailResponse = invitationQueryService.findById(invitationId);
            // then
            assertEquals(invitationDetailResponse.invitationId(), invitation.getId());
            assertEquals(invitationDetailResponse.weddingDateTime(), invitation.getWeddingDateTime().toString());
            assertEquals(invitationDetailResponse.weddingPlace(), invitation.getPlace());
            assertEquals(invitationDetailResponse.content(), invitation.getContent());
            assertEquals(invitationDetailResponse.invitationImageName(), invitation.getInvitationImage().getImageName());

            assertEquals(invitationDetailResponse.groomName(),couple.getGroom().getName());
            assertEquals(invitationDetailResponse.brideName(),couple.getBride().getName());
            assertEquals(invitationDetailResponse.coupleId(),couple.getId());
            
        }
    }
}