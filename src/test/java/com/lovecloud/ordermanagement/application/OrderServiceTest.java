package com.lovecloud.ordermanagement.application;

import com.lovecloud.auth.domain.Password;
import com.lovecloud.auth.domain.WeddingUserRepository;
import com.lovecloud.fundingmanagement.domain.Funding;
import com.lovecloud.fundingmanagement.domain.FundingStatus;
import com.lovecloud.fundingmanagement.domain.repository.FundingRepository;
import com.lovecloud.ordermanagement.application.command.CreateOrderCommand;
import com.lovecloud.ordermanagement.domain.Order;
import com.lovecloud.ordermanagement.domain.OrderDetails;
import com.lovecloud.ordermanagement.domain.repository.OrderDetailsRepository;
import com.lovecloud.ordermanagement.domain.repository.OrderRepository;
import com.lovecloud.productmanagement.domain.*;
import com.lovecloud.productmanagement.domain.repository.*;
import com.lovecloud.usermanagement.domain.*;
import com.lovecloud.usermanagement.domain.repository.CoupleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {
    @Autowired
    private OrderService orderService;

    @Autowired
    private CoupleRepository coupleRepository;

    @Autowired
    private WeddingUserRepository weddingUserRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductOptionsRepository productOptionsRepository;

    @Autowired
    private MainImageRepository mainImageRepository;

    @Autowired
    private DescriptionImageRepository descriptionImageRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private FundingRepository fundingRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    private WeddingUser groom;
    private Couple couple;

    private Funding funding;

    /**
     * 객체의 필드 값을 설정하는 메소드. Reflection을 사용하여 private 필드에 값을 설정할 때 사용
     */
    private void setField(Object target, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }
    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        // 신랑 객체 생성
        groom = WeddingUser.builder()
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

        //카테고리 생성
        Category category = Category.builder()
                .categoryName("카테고리1")
                .build();

        categoryRepository.save(category);

        //Product 생성
        Product product = Product.builder()
                .productName("상품1")
                .category(category)
                .build();

        productRepository.save(product);


        //ProductOptions 생성
        ProductOptions productOptions = ProductOptions.builder()
                .product(product)
                .price(10000L)
                .stockQuantity(100)
                .color("red")
                .modelName("model1")
                .build();

        productOptionsRepository.save(productOptions);

        //MainImage 생성
        MainImage mainImage = MainImage.builder()
                .mainImageName("mainImage1")
                .productOptions(productOptions)
                .build();

        mainImageRepository.save(mainImage);

        //descriptImage 생성
        DescriptionImage descriptImage = DescriptionImage.builder()
                .descriptionImageName("descriptImage1")
                .productOptions(productOptions)
                .build();

        descriptionImageRepository.save(descriptImage);

        //펀딩 생성
        funding = Funding.builder()
                .title("펀딩1")
                .message("펀딩1 내용")
                .targetAmount(100000L)
                .endDate(LocalDateTime.now().plusDays(1))
                .productOptions(productOptions)
                .couple(couple)
                .build();

        fundingRepository.save(funding);

        setField(funding, "id", 7219L);
        setField(funding, "status", FundingStatus.COMPLETED);
        setField(funding, "currentAmount", 100000L);



    }

    @Nested
    class 주문_생성_시 {

        @Test
        void 정상적으로_주문을_생성한다() {


            // given
            //주문 request 생성
            CreateOrderCommand command = new CreateOrderCommand(
                    groom.getId(),
                    Collections.singletonList(funding.getId()),
                    "Yeom",
                    "010-1234-5678",
                    "잘 배송해주세요",
                    "차은상",
                    "010-1234-5678",
                    "차은상네 집",
                    "12345",
                    "서울시 강남구",
                    "상세주소",
                    "배송 메모"
            );


            // when
            Long orderId = orderService.createOrder(command);

            // then
            Order order = orderRepository.findById(orderId).orElseThrow();
            assertEquals(command.userId(), order.getCouple().getGroom().getId());
            assertEquals(command.fundingIds(), orderDetailsRepository.findAllByOrderId(order.getId()).stream().map(OrderDetails::getFunding).map(Funding::getId).collect(Collectors.toList()));
            assertEquals(command.ordererName(), order.getOrdererName());
            assertEquals(command.ordererPhoneNumber(), order.getOrdererPhoneNumber());
            assertEquals(command.ordererMemo(), order.getOrdererMemo());
            assertEquals(command.receiverName(), order.getDelivery().getReceiverName());
            assertEquals(command.receiverPhoneNumber(), order.getDelivery().getReceiverPhoneNumber());
            assertEquals(command.deliveryName(), order.getDelivery().getDeliveryName());
            assertEquals(command.zipCode(), order.getDelivery().getZipCode());
            assertEquals(command.address(), order.getDelivery().getAddress());
            assertEquals(command.detailAddress(), order.getDelivery().getDetailAddress());
            assertEquals(command.deliveryMemo(), order.getDelivery().getDeliveryMemo());

        }


    }
}