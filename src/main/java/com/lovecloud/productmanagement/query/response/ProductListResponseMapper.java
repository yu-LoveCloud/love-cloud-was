package com.lovecloud.productmanagement.query.response;

import com.lovecloud.productmanagement.domain.MainImage;
import com.lovecloud.productmanagement.domain.Product;
import com.lovecloud.productmanagement.domain.ProductOptions;
import com.lovecloud.productmanagement.domain.repository.MainImageRepository;
import java.util.List;
import java.util.stream.Collectors;


public class ProductListResponseMapper {

    public static ProductListResponse mapProductToProductListResponse(
            Product product,
            List<ProductOptions> options,
            MainImageRepository mainImageRepository
    ) {
        List<ProductListResponse.ProductOptionSummary> optionSummaries = options.stream()
                .map(option -> mapProductOptionToProductOptionSummary(option, mainImageRepository))
                .collect(Collectors.toList());

        return new ProductListResponse(
                product.getId(),
                product.getProductName(),
                optionSummaries
        );
    }

    private static ProductListResponse.ProductOptionSummary mapProductOptionToProductOptionSummary(
            ProductOptions option,
            MainImageRepository mainImageRepository
    ) {
        List<MainImage> mainImages = mainImageRepository.findByProductOptionsId(option.getId());
        List<ProductListResponse.ProductOptionSummary.ImageData> imageDatas = mainImages.stream()
                .map(image -> new ProductListResponse.ProductOptionSummary.ImageData(
                        image.getId(),
                        image.getMainImageName()
                ))
                .collect(Collectors.toList());

        return new ProductListResponse.ProductOptionSummary(
                option.getId(),
                option.getColor(),
                option.getModelName(),
                option.getPrice(),
                option.getStockQuantity(),
                imageDatas
        );
    }
}
