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
@Table(name = "product_options")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductOptions extends CommonRootEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_options_id")
    private Long id;

    @Column(name = "color", nullable = false, length = 100)
    private String color;

    @Column(name = "model_name", nullable = false, length = 100)
    private String modelName;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @Column(name = "stock_quantity", nullable = false)
    private Integer stockQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Builder
    public ProductOptions(String color, String modelName, Integer price, Integer stockQuantity,
            Product product) {
        this.color = color;
        this.modelName = modelName;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.product = product;
    }
}
