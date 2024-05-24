package com.lovecloud.productmanagement.presentation;

import com.lovecloud.productmanagement.application.ProductCreationService;
import com.lovecloud.productmanagement.application.ProductOptionsCreationService;
import com.lovecloud.productmanagement.presentation.request.CreateProductOptionsRequest;
import com.lovecloud.productmanagement.presentation.request.CreateProductRequest;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/admin/products")
@RestController
public class ProductCreationController {

    private final ProductCreationService productCreationService;
    private final ProductOptionsCreationService productOptionsCreationService;

    @PostMapping
    public ResponseEntity<Long> createProduct(
            @Valid @RequestBody CreateProductRequest request
    ) {
        final Long productId = productCreationService.createProduct(request.toCommand());
        return ResponseEntity.created(URI.create("/admin/products/" + productId)).build();
    }

    @PostMapping("/{productId}/options")
    public ResponseEntity<Void> addProductOptions(
            @PathVariable Long productId,
            @Valid @RequestBody CreateProductOptionsRequest request
    ) {
        final Long optionId = productOptionsCreationService.addProductOptions(
                request.toCommand(productId));
        return ResponseEntity.created(
                        URI.create("/admin/products/" + productId + "/options/" + optionId))
                .build();
    }
}
