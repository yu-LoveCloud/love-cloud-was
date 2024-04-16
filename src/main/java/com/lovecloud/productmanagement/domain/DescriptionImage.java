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

    @Column(name = "description_image_url", nullable = false, length = 100)
    private String descriptionImageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Builder
    public DescriptionImage(String descriptionImageUrl, Product product) {
        this.descriptionImageUrl = descriptionImageUrl;
        this.product = product;
    }
}
