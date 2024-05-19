package com.lovecloud.productmanagement.application;

import com.lovecloud.productmanagement.application.command.CreateProductCommand;
import com.lovecloud.productmanagement.domain.Category;
import com.lovecloud.productmanagement.domain.DescriptionImage;
import com.lovecloud.productmanagement.domain.MainImage;
import com.lovecloud.productmanagement.domain.Product;
import com.lovecloud.productmanagement.domain.repository.CategoryRepository;
import com.lovecloud.productmanagement.domain.repository.DescriptionImageRepository;
import com.lovecloud.productmanagement.domain.repository.MainImageRepository;
import com.lovecloud.productmanagement.domain.repository.ProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ProductCreateService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final MainImageRepository mainImageRepository;
    private final DescriptionImageRepository descriptionImageRepository;

    public Long createProduct(CreateProductCommand command) {
        Category category = categoryRepository.findByIdOrThrow(command.categoryId());
        Product product = command.toProduct(category);
        product = productRepository.save(product);
        createAndSaveImages(command, product);
        return product.getId();
    }

    private void createAndSaveImages(CreateProductCommand command, Product product) {
        List<MainImage> mainImages = command.toMainImages(product);
        mainImageRepository.saveAll(mainImages);
        List<DescriptionImage> descriptionImages = command.toDescriptionImages(product);
        descriptionImageRepository.saveAll(descriptionImages);
    }
}
