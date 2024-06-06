package com.lovecloud.productmanagement.application;

import com.lovecloud.productmanagement.application.command.CreateProductOptionsCommand;
import com.lovecloud.productmanagement.domain.Product;
import com.lovecloud.productmanagement.domain.ProductOptions;
import com.lovecloud.productmanagement.domain.repository.ProductOptionsRepository;
import com.lovecloud.productmanagement.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ProductOptionsCreationService {

    private final ProductRepository productRepository;
    private final ProductOptionsRepository productOptionsRepository;

    public Long addProductOptions(CreateProductOptionsCommand command) {
        Product product = productRepository.findByIdOrThrow(command.productId());
        ProductOptions options = command.toProductOptions(product);
        command.toMainImages(options).forEach(options::addMainImage);
        command.toDescriptionImages(options).forEach(options::addDescriptionImage);
        return productOptionsRepository.save(options).getId();
    }
}
