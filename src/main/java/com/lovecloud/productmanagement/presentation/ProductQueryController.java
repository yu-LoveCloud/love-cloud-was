package com.lovecloud.productmanagement.presentation;

import com.lovecloud.productmanagement.application.ProductQueryService;
import com.lovecloud.productmanagement.query.response.ProductDetailResponse;
import com.lovecloud.productmanagement.query.response.ProductListResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ProductQueryController {

    private final ProductQueryService productQueryService;

    @GetMapping("/products")
    public ResponseEntity<List<ProductListResponse>> listProducts(
            @RequestParam(required = false) Long categoryId
    ) {
        final List<ProductListResponse> products = productQueryService.findAllByCategoryId(
                categoryId);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/items/{productOptionsId}")
    public ResponseEntity<ProductDetailResponse> detailProduct(
            @PathVariable Long productOptionsId
    ) {
        final ProductDetailResponse product = productQueryService.findById(productOptionsId);
        return ResponseEntity.ok(product);
    }
}
