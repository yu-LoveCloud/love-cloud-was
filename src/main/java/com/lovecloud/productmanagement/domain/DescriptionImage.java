package com.lovecloud.productmanagement.domain;

import com.lovecloud.global.domain.CommonRootEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "description_image")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DescriptionImage extends CommonRootEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "description_image_id")
    private Long id;

    @Column(name = "description_image_name", nullable = false, length = 100)
    private String descriptionImageName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_options_id", nullable = false)
    private ProductOptions productOptions;

    @Builder
    public DescriptionImage(String descriptionImageName, ProductOptions productOptions) {
        this.descriptionImageName = descriptionImageName;
        this.productOptions = productOptions;
    }

    public static DescriptionImage createDescriptionImage(String descriptionImageName, ProductOptions productOptions) {
        return DescriptionImage.builder()
                .descriptionImageName(descriptionImageName)
                .productOptions(productOptions)
                .build();
    }

    public void setProductOptions(ProductOptions productOptions) {
        this.productOptions = productOptions;
        if (!productOptions.getDescriptionImages().contains(this)) {
            productOptions.getDescriptionImages().add(this);
        }
    }
}
