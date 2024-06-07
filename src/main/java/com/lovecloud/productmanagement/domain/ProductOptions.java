package com.lovecloud.productmanagement.domain;

import com.lovecloud.global.domain.CommonRootEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private Long price;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @Column(name = "stock_quantity", nullable = false)
    private Integer stockQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @OneToMany(mappedBy = "productOptions", fetch = FetchType.LAZY)
    private List<MainImage> mainImages;

    @Builder
    public ProductOptions(String color, String modelName, Long price, Integer stockQuantity,
            Product product) {
        this.color = color;
        this.modelName = modelName;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.product = product;
    }
}
