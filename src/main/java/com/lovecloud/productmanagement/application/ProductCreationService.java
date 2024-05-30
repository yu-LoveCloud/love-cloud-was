package com.lovecloud.productmanagement.application;

import com.lovecloud.productmanagement.application.command.CreateProductCommand;
import com.lovecloud.productmanagement.domain.Category;
import com.lovecloud.productmanagement.domain.Product;
import com.lovecloud.productmanagement.domain.repository.CategoryRepository;
import com.lovecloud.productmanagement.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ProductCreationService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public Long createProduct(CreateProductCommand command) {
        Category category = categoryRepository.findByIdOrThrow(command.categoryId());
        Product product = command.toProduct(category);
        return productRepository.save(product).getId();
    }
}
