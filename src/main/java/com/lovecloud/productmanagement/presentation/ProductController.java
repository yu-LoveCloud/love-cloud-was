package com.lovecloud.productmanagement.presentation;

import com.lovecloud.productmanagement.application.ProductCreateService;
import com.lovecloud.productmanagement.presentation.request.CreateProductRequest;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/products")
@RestController
public class ProductController {

    private final ProductCreateService productCreateService;

    @PostMapping
    public ResponseEntity<Long> creatProduct(
            @Valid @RequestBody CreateProductRequest request
    ) {
        final Long productId = productCreateService.createProduct(request.toCommand());
        return ResponseEntity.created(URI.create("/products/" + productId)).build();
    }

/*    @PostMapping("/{productId}/options")
    public ResponseEntity<Void> addProductOptions(
            @PathVariable Long productId,
            @Valid @RequestBody CreateProductOptionRequest request
    ) {
        productCreateService.addProductOptions(request.toCommand(productId));
        return ResponseEntity.created(URI.create("/products/" + productId + "/options")).build();
    }*/
}
