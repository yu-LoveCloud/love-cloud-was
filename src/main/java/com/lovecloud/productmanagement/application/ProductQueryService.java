package com.lovecloud.productmanagement.application;

import com.lovecloud.productmanagement.domain.DescriptionImage;
import com.lovecloud.productmanagement.domain.MainImage;
import com.lovecloud.productmanagement.domain.Product;
import com.lovecloud.productmanagement.domain.ProductOptions;
import com.lovecloud.productmanagement.domain.repository.DescriptionImageRepository;
import com.lovecloud.productmanagement.domain.repository.MainImageRepository;
import com.lovecloud.productmanagement.domain.repository.ProductOptionsRepository;
import com.lovecloud.productmanagement.domain.repository.ProductRepository;
import com.lovecloud.productmanagement.query.response.ProductDetailResponse;
import com.lovecloud.productmanagement.query.response.ProductDetailResponseMapper;
import com.lovecloud.productmanagement.query.response.ProductListResponse;
import com.lovecloud.productmanagement.query.response.ProductListResponseMapper;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ProductQueryService {

    private final ProductRepository productRepository;
    private final ProductOptionsRepository productOptionsRepository;
    private final MainImageRepository mainImageRepository;
    private final DescriptionImageRepository descriptionImageRepository;


    public List<ProductListResponse> findAllByCategoryId(Long categoryId) {
        List<Product> products = categoryId == null
                ? productRepository.findAll()
                : productRepository.findByCategoryId(categoryId);
        return products.stream()
                .map(this::mapProductToProductListResponse)
                .collect(Collectors.toList());
    }

    public ProductDetailResponse findById(Long productOptionsId) {
        ProductOptions selectedOption = productOptionsRepository.findByIdAndIsDeletedOrThrow(
                productOptionsId, false);
        List<MainImage> mainImages = mainImageRepository.findByProductOptionsId(
                selectedOption.getId());
        List<DescriptionImage> descriptionImages = descriptionImageRepository.findByProductOptionsId(
                selectedOption.getId());
        List<ProductOptions> otherOptions = productOptionsRepository.findOthersByProductId(
                selectedOption.getProduct().getId(), selectedOption.getId());
        return ProductDetailResponseMapper.mapProductOptionsToProductDetailResponse(selectedOption,
                mainImages, descriptionImages, otherOptions);
    }

    private ProductListResponse mapProductToProductListResponse(Product product) {
        List<ProductOptions> validOptions = productOptionsRepository
                .findByProductIdAndIsDeleted(product.getId(), false);
        if (validOptions.isEmpty()) {
            return null;
        }
        ProductOptions selectedOption = validOptions.get(0);
        List<MainImage> selectedOptionMainImages = mainImageRepository.findByProductOptionsId(
                selectedOption.getId());
        List<ProductOptions> otherOptions = validOptions.size() > 1
                ? validOptions.subList(1, validOptions.size())
                : Collections.emptyList();
        return ProductListResponseMapper
                .mapProductToProductListResponse(product, selectedOption, selectedOptionMainImages,
                        otherOptions);
    }
}
