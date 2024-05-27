package com.lovecloud.productmanagement.application;

import com.lovecloud.productmanagement.application.command.CreateProductOptionsCommand;
import com.lovecloud.productmanagement.domain.DescriptionImage;
import com.lovecloud.productmanagement.domain.MainImage;
import com.lovecloud.productmanagement.domain.Product;
import com.lovecloud.productmanagement.domain.ProductOptions;
import com.lovecloud.productmanagement.domain.repository.DescriptionImageRepository;
import com.lovecloud.productmanagement.domain.repository.MainImageRepository;
import com.lovecloud.productmanagement.domain.repository.ProductOptionsRepository;
import com.lovecloud.productmanagement.domain.repository.ProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ProductOptionsCreateService {

    private final ProductRepository productRepository;
    private final ProductOptionsRepository productOptionsRepository;
    private final MainImageRepository mainImageRepository;
    private final DescriptionImageRepository descriptionImagesRepository;

    public Long addProductOptions(CreateProductOptionsCommand command) {
        Product product = productRepository.findByIdOrThrow(command.productId());
        ProductOptions options = command.toProductOptions(product);
        options = productOptionsRepository.save(options);
        createAndSaveImages(command, options);
        return options.getId();
    }

    private void createAndSaveImages(CreateProductOptionsCommand command, ProductOptions options) {
        List<MainImage> mainImages = command.toMainImages(options);
        mainImageRepository.saveAll(mainImages);
        List<DescriptionImage> descriptionImages = command.toDescriptionImages(options);
        descriptionImagesRepository.saveAll(descriptionImages);
    }
}
