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
@Table(name = "main_image")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MainImage extends CommonRootEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "main_image_id")
    private Long id;

    @Column(name = "main_image_name", nullable = false, length = 100)
    private String mainImageName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_options_id", nullable = false)
    private ProductOptions productOptions;

    @Builder
    public MainImage(String mainImageName, ProductOptions productOptions) {
        this.mainImageName = mainImageName;
        this.productOptions = productOptions;
    }

    public void setProductOptions(ProductOptions productOptions) {
        this.productOptions = productOptions;
        if (!productOptions.getMainImages().contains(this)) {
            productOptions.getMainImages().add(this);
        }
    }
}
