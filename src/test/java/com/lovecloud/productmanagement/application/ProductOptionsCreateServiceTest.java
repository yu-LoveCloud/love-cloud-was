package com.lovecloud.productmanagement.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.lovecloud.productmanagement.application.command.CreateProductCommand;
import com.lovecloud.productmanagement.application.command.CreateProductOptionsCommand;
import com.lovecloud.productmanagement.domain.Category;
import com.lovecloud.productmanagement.domain.Product;
import com.lovecloud.productmanagement.domain.ProductOptions;
import com.lovecloud.productmanagement.domain.repository.CategoryRepository;
import com.lovecloud.productmanagement.domain.repository.ProductOptionsRepository;
import com.lovecloud.productmanagement.domain.repository.ProductRepository;
import com.lovecloud.productmanagement.exception.NotFoundProductException;
import jakarta.transaction.Transactional;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
@DisplayName("상품 옵션 생성 서비스 (ProductOptionsCreateService) 은(는)")
@DisplayNameGeneration(ReplaceUnderscores.class)
class ProductOptionsCreateServiceTest {

    @Autowired
    private ProductOptionsCreateService productOptionsCreateService;

    @Autowired
    private ProductCreateService productCreateService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductOptionsRepository productOptionsRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private Product product;

    private Category category;

    @BeforeEach
    void setUp() {
        category = new Category("에어컨");
        category = categoryRepository.save(category);

        CreateProductCommand command = CreateProductCommand.builder()
                .productName("BESPOKE 무풍에어컨 갤러리 청정")
                .categoryId(category.getId())
                .mainImageNames(List.of("image1.jpg"))
                .descriptionImageNames(List.of("detail1.jpg", "detail2.jpg"))
                .build();
        Long productId = productCreateService.createProduct(command);
        product = productRepository.findByIdOrThrow(productId);
    }

    @Nested
    class 상품_옵션_생성_시 {

        @Test
        void 정상적으로_상품_옵션을_생성한다() {
            // given
            CreateProductOptionsCommand command = CreateProductOptionsCommand.builder()
                    .productId(product.getId())
                    .color("white")
                    .modelName("AR06D1150HZT")
                    .price(1000000)
                    .stockQuantity(10)
                    .build();

            // when
            Long optionId = productOptionsCreateService.addProductOptions(command);

            // then
            ProductOptions options = productOptionsRepository.getById(optionId);
            assertEquals(product, options.getProduct());
            assertEquals("white", options.getColor());
            assertEquals("AR06D1150HZT", options.getModelName());
            assertEquals(1000000, options.getPrice());
            assertEquals(10, options.getStockQuantity());
            assertEquals(false, options.getIsDeleted());
        }

        @Test
        @DisplayName("상품이 존재하지 않는 경우 예외를 발생시킨다")
        void 상품이_존재하지_않는_경우_예외를_발생시키다() {
            // given
            CreateProductOptionsCommand command = CreateProductOptionsCommand.builder()
                    .productId(999L) // 존재하지 않는 상품 ID
                    .color("white")
                    .modelName("AR06D1150HZT")
                    .price(1000000)
                    .stockQuantity(10)
                    .build();

            // when & then
            assertThrows(NotFoundProductException.class,
                    () -> productOptionsCreateService.addProductOptions(command));
        }
    }
}