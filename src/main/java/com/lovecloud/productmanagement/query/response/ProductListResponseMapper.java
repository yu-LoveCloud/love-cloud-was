package com.lovecloud.productmanagement.query.response;

import com.lovecloud.productmanagement.domain.MainImage;
import com.lovecloud.productmanagement.domain.Product;
import com.lovecloud.productmanagement.domain.ProductOptions;
import java.util.List;
import java.util.stream.Collectors;

public class ProductListResponseMapper {

    public static ProductListResponse mapProductToProductListResponse(
            Product product,
            ProductOptions selectedOption,
            List<MainImage> selectedOptionMainImages,
            List<ProductOptions> otherOptions
    ) {
        return new ProductListResponse(
                product.getId(),
                product.getProductName(),
                new ProductListResponse.CategoryData(
                        product.getCategory().getId(),
                        product.getCategory().getCategoryName()
                ),
                mapSelectedOption(selectedOption, selectedOptionMainImages),
                mapOtherOptions(otherOptions)
        );
    }

    private static ProductListResponse.ProductOptionSummary mapSelectedOption(ProductOptions option,
            List<MainImage> images) {
        return new ProductListResponse.ProductOptionSummary(
                option.getId(),
                option.getColor(),
                option.getModelName(),
                option.getPrice(),
                option.getStockQuantity(),
                mapMainImages(images)
        );
    }

    private static List<ProductListResponse.OtherOptionData> mapOtherOptions(
            List<ProductOptions> otherOptions) {
        return otherOptions.stream()
                .map(option -> new ProductListResponse.OtherOptionData(
                        option.getId(),
                        option.getColor(),
                        option.getStockQuantity()))
                .collect(Collectors.toList());
    }

    private static List<ProductListResponse.ImageData> mapMainImages(List<MainImage> images) {
        return images.stream()
                .map(image -> new ProductListResponse.ImageData(
                        image.getId(),
                        image.getMainImageName()
                ))
                .collect(Collectors.toList());
    }
}
