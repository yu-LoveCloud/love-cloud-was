package com.lovecloud.productmanagement.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.lovecloud.productmanagement.application.command.CreateProductCommand;
import com.lovecloud.productmanagement.domain.Category;
import com.lovecloud.productmanagement.domain.Product;
import com.lovecloud.productmanagement.domain.repository.CategoryRepository;
import com.lovecloud.productmanagement.domain.repository.ProductRepository;
import com.lovecloud.productmanagement.exception.NotFoundCategoryException;
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
@DisplayName("상품 생성 서비스 (ProductCreateService) 은(는)")
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class ProductCreateServiceTest {

    @Autowired
    private ProductCreateService productCreateService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private Category category;

    @BeforeEach
    void setUp() {
        category = new Category("에어컨");
        category = categoryRepository.save(category);
    }

    @Nested
    class 상품_생성_시 {

        @Test
        void 정상적으로_상품과_이미지를_생성한다() {
            // given
            CreateProductCommand command = CreateProductCommand.builder()
                    .productName("BESPOKE 무풍에어컨 갤러리 청정")
                    .categoryId(category.getId())
                    .mainImageNames(List.of("image1.jpg"))
                    .descriptionImageNames(List.of("detail1.jpg", "detail2.jpg"))
                    .build();

            // when
            Long productId = productCreateService.createProduct(command);

            // then
            Product product = productRepository.findById(productId).orElseThrow();
            assertThat(product.getProductName()).isEqualTo("BESPOKE 무풍에어컨 갤러리 청정");
            assertThat(product.getCategory()).isEqualTo(category);
        }

        @Test
        void 카테고리가_존재하지_않는_경우_예외를_발생시킨다() {
            // given
            CreateProductCommand command = CreateProductCommand.builder()
                    .productName("BESPOKE 무풍에어컨 갤러리 청정")
                    .categoryId(999L) // 존재하지 않는 카테고리 ID
                    .mainImageNames(List.of("image1.jpg"))
                    .descriptionImageNames(List.of("detail1.jpg", "detail2.jpg"))
                    .build();

            // when & then
            assertThatThrownBy(() -> productCreateService.createProduct(command))
                    .isInstanceOf(NotFoundCategoryException.class);
        }

        @Test
        void 제품명이_중복되어도_생성된다() {
            // given
            productCreateService.createProduct(CreateProductCommand.builder()
                    .productName("BESPOKE 무풍에어컨 갤러리 청정")
                    .categoryId(category.getId())
                    .mainImageNames(List.of("image1.jpg"))
                    .descriptionImageNames(List.of("detail1.jpg"))
                    .build());

            CreateProductCommand secondCommand = CreateProductCommand.builder()
                    .productName("BESPOKE 무풍에어컨 갤러리 청정")
                    .categoryId(category.getId())
                    .mainImageNames(List.of("image2.jpg"))
                    .descriptionImageNames(List.of("detail2.jpg", "detail3.jpg"))
                    .build();

            // when
            Long secondProductId = productCreateService.createProduct(secondCommand);

            // then
            Product secondProduct = productRepository.findById(secondProductId).orElseThrow();
            assertThat(secondProduct.getProductName()).isEqualTo("BESPOKE 무풍에어컨 갤러리 청정");
        }
    }
}