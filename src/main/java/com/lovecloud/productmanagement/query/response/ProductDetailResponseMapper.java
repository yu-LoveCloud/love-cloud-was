package com.lovecloud.productmanagement.query.response;

import com.lovecloud.productmanagement.domain.DescriptionImage;
import com.lovecloud.productmanagement.domain.MainImage;
import com.lovecloud.productmanagement.domain.ProductOptions;
import com.lovecloud.productmanagement.query.response.ProductDetailResponse.OtherOptionData;
import com.lovecloud.productmanagement.query.response.ProductDetailResponse.ProductOptionDetail;
import java.util.List;
import java.util.stream.Collectors;

public class ProductDetailResponseMapper {

    public static ProductDetailResponse map(ProductOptions selectedOption, List<ProductOptions> otherOptions) {
        return new ProductDetailResponse(
                selectedOption.getProduct().getId(),
                selectedOption.getProduct().getProductName(),
                mapSelectedOptionDetail(selectedOption),
                mapOtherOptions(otherOptions)
        );
    }

    private static ProductOptionDetail mapSelectedOptionDetail(ProductOptions option) {
        return new ProductOptionDetail(
                option.getId(),
                option.getColor(),
                option.getModelName(),
                option.getPrice(),
                option.getStockQuantity(),
                mapMainImages(option.getMainImages()),
                mapDescriptionImages(option.getDescriptionImages())
        );
    }

    private static List<OtherOptionData> mapOtherOptions(List<ProductOptions> otherOptions) {
        return otherOptions.stream()
                .map(option -> new OtherOptionData(
                        option.getId(),
                        option.getColor(),
                        option.getStockQuantity()))
                .collect(Collectors.toList());
    }

    private static List<ProductOptionDetail.ImageData> mapMainImages(List<MainImage> images) {
        return images.stream()
                .map(image -> new ProductOptionDetail.ImageData(
                        image.getId(),
                        image.getMainImageName()
                ))
                .collect(Collectors.toList());
    }

    private static List<ProductOptionDetail.ImageData> mapDescriptionImages(List<DescriptionImage> images) {
        return images.stream()
                .map(image -> new ProductOptionDetail.ImageData(
                        image.getId(),
                        image.getDescriptionImageName()
                ))
                .collect(Collectors.toList());
    }
}
