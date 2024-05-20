package com.lovecloud.productmanagement.presentation;

import com.lovecloud.productmanagement.application.ProductQueryService;
import com.lovecloud.productmanagement.query.response.ProductResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/products")
@RestController
public class ProductQueryController {

    private final ProductQueryService productQueryService;

    @GetMapping
    public ResponseEntity<List<ProductResponse>> listProducts(
            @RequestParam(required = false) Long categoryId
    ) {
        final List<ProductResponse> products = productQueryService.findAllByCategoryId(categoryId);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> detailProduct(
            @PathVariable Long productId
    ) {
        final ProductResponse product = productQueryService.findById(productId);
        return ResponseEntity.ok(product);
    }
}
