package com.lovecloud.productmanagement.application;

import com.lovecloud.productmanagement.domain.Product;
import com.lovecloud.productmanagement.domain.ProductOptions;
import com.lovecloud.productmanagement.domain.repository.ProductOptionsRepository;
import com.lovecloud.productmanagement.domain.repository.ProductRepository;
import com.lovecloud.productmanagement.query.response.ProductDetailResponse;
import com.lovecloud.productmanagement.query.response.ProductDetailResponseMapper;
import com.lovecloud.productmanagement.query.response.ProductListResponse;
import com.lovecloud.productmanagement.query.response.ProductListResponseMapper;
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

    public List<ProductListResponse> findAllByCategoryId(Long categoryId) {
        List<Product> products = categoryId == null
                ? productRepository.findAll()
                : productRepository.findByCategoryId(categoryId);
        return products.stream()
                .map(ProductListResponseMapper::map)
                .collect(Collectors.toList());
    }

    public ProductDetailResponse findById(Long productOptionsId) {
        ProductOptions selectedOption = productOptionsRepository.findByIdAndIsDeletedOrThrow(
                productOptionsId, false);
        List<ProductOptions> otherOptions = productOptionsRepository.findOthersByProductId(
                selectedOption.getProduct().getId(), selectedOption.getId());
        return ProductDetailResponseMapper.map(selectedOption, otherOptions);
    }
}
