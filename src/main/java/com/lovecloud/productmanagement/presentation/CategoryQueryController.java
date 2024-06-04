package com.lovecloud.productmanagement.presentation;

import com.lovecloud.productmanagement.application.CategoryQueryService;
import com.lovecloud.productmanagement.query.response.CategoryListResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/categories")
public class CategoryQueryController {

    private final CategoryQueryService categoryQueryService;

    @GetMapping
    public ResponseEntity<List<CategoryListResponse>> listCategories() {
        final List<CategoryListResponse> categories = categoryQueryService.findAll();
        return ResponseEntity.ok(categories);
    }
}
