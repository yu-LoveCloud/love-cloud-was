package com.lovecloud.productmanagement.query.response;

import com.lovecloud.productmanagement.domain.DescriptionImage;
import com.lovecloud.productmanagement.domain.MainImage;
import com.lovecloud.productmanagement.domain.Product;
import com.lovecloud.productmanagement.domain.ProductOptions;
import java.util.List;
import java.util.stream.Collectors;

public class ProductResponseAdapter {

    public static ProductResponse fromProduct(
            Product product,
            List<MainImage> mainImages,
            List<DescriptionImage> descriptionImages,
            List<ProductOptions> productOptions
    ) {
        return new ProductResponse(
                product.getId(),
                product.getProductName(),
                new ProductResponse.CategoryData(
                        product.getCategory().getId(),
                        product.getCategory().getCategoryName()
                ),
                mapMainImages(mainImages),
                mapDescriptionImages(descriptionImages),
                mapProductOptions(productOptions)
        );
    }

    private static List<ProductResponse.ImageData> mapMainImages(List<MainImage> mainImages) {
        return mainImages.stream()
                .map(image -> new ProductResponse.ImageData(
                        image.getId(),
                        image.getMainImageName()
                ))
                .collect(Collectors.toList());
    }

    private static List<ProductResponse.ImageData> mapDescriptionImages(
            List<DescriptionImage> descriptionImages) {
        return descriptionImages.stream()
                .map(image -> new ProductResponse.ImageData(
                        image.getId(),
                        image.getDescriptionImageName()
                ))
                .collect(Collectors.toList());
    }

    private static List<ProductResponse.ProductOptionsData> mapProductOptions(
            List<ProductOptions> productOptions) {
        return productOptions.stream()
                .map(option -> new ProductResponse.ProductOptionsData(
                        option.getId(),
                        option.getColor(),
                        option.getModelName(),
                        option.getIsDeleted(),
                        option.getPrice(),
                        option.getStockQuantity()
                ))
                .collect(Collectors.toList());
    }
}
