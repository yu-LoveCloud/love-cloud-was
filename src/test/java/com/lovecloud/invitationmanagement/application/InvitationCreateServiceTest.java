package com.lovecloud.invitationmanagement.application;

import com.lovecloud.invitationmanagement.application.command.CreateInvitationCommand;
import com.lovecloud.invitationmanagement.domain.Invitation;
import com.lovecloud.invitationmanagement.domain.InvitationImage;
import com.lovecloud.invitationmanagement.domain.repository.InvitationImageRepository;
import com.lovecloud.invitationmanagement.domain.repository.InvitationRepository;
import com.lovecloud.invitationmanagement.exeption.NotFoundInvitationImageException;
import com.lovecloud.usermanagement.application.CoupleService;
import com.lovecloud.usermanagement.domain.*;
import com.lovecloud.usermanagement.exeption.NotFoundCoupleException;
import com.lovecloud.usermanagement.repository.CoupleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class InvitationCreateServiceTest {
    @Autowired
    private InvitationCreateService invitationCreateService;

    @Autowired
    private CoupleService coupleService;

    @Autowired
    private InvitationRepository invitationRepository;

    @Autowired
    private InvitationImageRepository invitationImageRepository;

    @Autowired
    private CoupleRepository coupleRepository;

    @Autowired
    private WeddingUserRepository weddingUserRepository;

    private WeddingUser groom;
    private WeddingUser solo_groom;
    private InvitationImage invitationImage;
    private Couple couple;
    @BeforeEach
    void setUp() {
        // 신랑 객체 생성
        groom = WeddingUser.builder()
                .email("groom@example.com")
                .name("John Doe")
                .userRole(UserRole.WEDDING_USER) // UserRole은 enum 또는 적절한 값으로 설정
                .phoneNumber("010-1234-5678")
                .password("securePassword1!") // 보통 해시된 비밀번호를 사용
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
                .password("securePassword2!") // 보통 해시된 비밀번호를 사용
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
        invitationImage = InvitationImage.builder()
                .imageName("image")
                .build();
        invitationImage = invitationImageRepository.save(invitationImage);

        //부부가 아닌 사용자 객체 생성
        solo_groom = WeddingUser.builder()
                .email("groom2@example.com")
                .name("John Doe2")
                .userRole(UserRole.WEDDING_USER) // UserRole은 enum 또는 적절한 값으로 설정
                .phoneNumber("010-2234-5678")
                .password("securePassword1!") // 보통 해시된 비밀번호를 사용
                .accountStatus(AccountStatus.ACTIVE) // AccountStatus는 enum 또는 적절한 값으로 설정
                .weddingRole(WeddingRole.GROOM) // WeddingRole은 enum 또는 적절한 값으로 설정
                .oauthId(null) // OAuth ID가 없으면 null
                .oauth(null) // OAuth 제공자가 없으면 null
                .invitationCode("INV123GROO3") // 임의의 초대 코드
                .build();
        solo_groom = weddingUserRepository.save(solo_groom);


    }

    @Nested
    class 초대장_생성_시 {

        @Test
        void 정상적으로_초대장을_생성한다() {


            // given
            CreateInvitationCommand command = CreateInvitationCommand.builder()
                    .invitationImageId(invitationImage.getId())
                    .weddingDateTime("2022-10-10T10:10:10")
                    .place("서울")
                    .content("초대합니다")
                    .build();

            // when
            Long invitationId = invitationCreateService.addInvitation(command);
            coupleService.updateCoupleInvitation(groom.getId(),invitationId);

            // then
            Invitation invitation = invitationRepository.findById(invitationId).orElseThrow();
            assertEquals(couple.getInvitation().getId(), invitation.getId());
            assertEquals(command.weddingDateTime(), invitation.getWeddingDateTime().toString());
            assertEquals(command.place(), invitation.getPlace());
            assertEquals(command.content(), invitation.getContent());
            assertEquals(command.invitationImageId(), invitation.getInvitationImage().getId());

        }

        @Test
        void 부부가_존재하지_않는_경우_예외를_발생시킨다() {
            // given
            CreateInvitationCommand command = CreateInvitationCommand.builder()
                    .weddingDateTime("2022-10-10T10:10:10")
                    .place("서울")
                    .content("초대합니다")
                    .invitationImageId(invitationImage.getId())
                    .build();

            //when
            Long invitationId = invitationCreateService.addInvitation(command);

            // when & then
            assertThatThrownBy(() -> coupleService.updateCoupleInvitation(solo_groom.getId(), invitationId))
                    .isInstanceOf(NotFoundCoupleException.class);
        }

        @Test
        void 이미지가_존재하지_않는_경우_예외를_발생시킨다() {
            // given
            CreateInvitationCommand command = CreateInvitationCommand.builder()
                    .invitationImageId(999L) // 존재하지 않는 이미지 ID
                    .weddingDateTime("2022-10-10T10:10:10")
                    .place("서울")
                    .content("초대합니다")
                    .build();

            // when & then
            assertThatThrownBy(() -> invitationCreateService.addInvitation(command))
                    .isInstanceOf(NotFoundInvitationImageException.class);
        }
    }

}