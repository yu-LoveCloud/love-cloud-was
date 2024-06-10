package com.lovecloud.productmanagement.query.response;

import com.lovecloud.productmanagement.domain.Product;
import com.lovecloud.productmanagement.domain.ProductOptions;
import java.util.List;
import java.util.stream.Collectors;


public class ProductListResponseMapper {

    public static ProductListResponse map(Product product) {
        List<ProductListResponse.ProductOptionSummary> optionSummaries = product.getProductOptions()
                .stream()
                .filter(option -> !option.getIsDeleted())
                .map(ProductListResponseMapper::mapProductOptionToProductOptionSummary)
                .collect(Collectors.toList());

        return new ProductListResponse(
                product.getId(),
                product.getProductName(),
                optionSummaries
        );
    }

    private static ProductListResponse.ProductOptionSummary mapProductOptionToProductOptionSummary(
            ProductOptions option) {
        List<ProductListResponse.ProductOptionSummary.ImageData> imageDatas = option.getMainImages()
                .stream()
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
