package com.lovecloud.productmanagement.query.response;

import com.lovecloud.productmanagement.domain.DescriptionImage;
import com.lovecloud.productmanagement.domain.MainImage;
import com.lovecloud.productmanagement.domain.ProductOptions;
import com.lovecloud.productmanagement.query.response.ProductDetailResponse.OtherOptionData;
import com.lovecloud.productmanagement.query.response.ProductDetailResponse.ProductOptionDetail;
import java.util.List;
import java.util.stream.Collectors;

public class ProductDetailResponseMapper {

    public static ProductDetailResponse mapProductOptionsToProductDetailResponse(
            ProductOptions selectedOption,
            List<MainImage> mainImages,
            List<DescriptionImage> descriptionImages,
            List<ProductOptions> otherOptions
    ) {
        return new ProductDetailResponse(
                selectedOption.getProduct().getId(),
                selectedOption.getProduct().getProductName(),
                new ProductDetailResponse.CategoryData(
                        selectedOption.getProduct().getCategory().getId(),
                        selectedOption.getProduct().getCategory().getCategoryName()
                ),
                mapSelectedOptionDetail(selectedOption, mainImages, descriptionImages),
                mapOtherOptions(otherOptions)
        );
    }

    private static ProductOptionDetail mapSelectedOptionDetail(
            ProductOptions option,
            List<MainImage> mainImages,
            List<DescriptionImage> descriptionImages
    ) {
        return new ProductDetailResponse.ProductOptionDetail(
                option.getId(),
                option.getColor(),
                option.getModelName(),
                option.getPrice(),
                option.getStockQuantity(),
                mapMainImages(mainImages),
                mapDescriptionImages(descriptionImages)
        );
    }

    private static List<OtherOptionData> mapOtherOptions(List<ProductOptions> otherOptions) {
        return otherOptions.stream()
                .map(option -> new ProductDetailResponse.OtherOptionData(
                        option.getId(),
                        option.getColor(),
                        option.getStockQuantity()))
                .collect(Collectors.toList());
    }

    private static List<ProductDetailResponse.ImageData> mapMainImages(List<MainImage> images) {
        return images.stream()
                .map(image -> new ProductDetailResponse.ImageData(
                        image.getId(),
                        image.getMainImageName()
                ))
                .collect(Collectors.toList());
    }

    private static List<ProductDetailResponse.ImageData> mapDescriptionImages(
            List<DescriptionImage> images) {
        return images.stream()
                .map(image -> new ProductDetailResponse.ImageData(
                        image.getId(),
                        image.getDescriptionImageName()
                ))
                .collect(Collectors.toList());
    }
}
