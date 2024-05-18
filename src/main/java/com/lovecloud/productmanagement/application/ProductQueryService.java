package com.lovecloud.productmanagement.application;

import com.lovecloud.productmanagement.domain.DescriptionImage;
import com.lovecloud.productmanagement.domain.MainImage;
import com.lovecloud.productmanagement.domain.Product;
import com.lovecloud.productmanagement.domain.ProductOptions;
import com.lovecloud.productmanagement.domain.repository.DescriptionImageRepository;
import com.lovecloud.productmanagement.domain.repository.MainImageRepository;
import com.lovecloud.productmanagement.domain.repository.ProductOptionsRepository;
import com.lovecloud.productmanagement.domain.repository.ProductRepository;
import com.lovecloud.productmanagement.query.response.ProductResponse;
import com.lovecloud.productmanagement.query.response.ProductResponseAdapter;
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

    public List<ProductResponse> findAllByCategoryId(Long categoryId) {
        List<Product> products = categoryId == null
                ? productRepository.findAll()
                : productRepository.findByCategoryId(categoryId);
        return products.stream()
                .map(this::mapProductToProductResponse)
                .collect(Collectors.toList());
    }

    public ProductResponse findById(Long productId) {
        Product product = productRepository.findByIdOrThrow(productId);
        return mapProductToProductResponse(product);
    }

    private ProductResponse mapProductToProductResponse(Product product) {
        List<MainImage> mainImages = mainImageRepository.findByProductId(product.getId());
        List<DescriptionImage> descriptionImages = descriptionImageRepository.findByProductId(
                product.getId());
        List<ProductOptions> productOptions = productOptionsRepository.findByProductId(
                product.getId());

        return ProductResponseAdapter.fromProduct(
                product,
                mainImages,
                descriptionImages,
                productOptions
        );
    }
}
