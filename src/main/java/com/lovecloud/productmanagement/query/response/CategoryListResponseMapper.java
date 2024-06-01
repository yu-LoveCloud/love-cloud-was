package com.lovecloud.productmanagement.query.response;

import com.lovecloud.productmanagement.domain.Category;

public class CategoryListResponseMapper {

    public static CategoryListResponse map(Category category) {
        return new CategoryListResponse(
                category.getId(),
                category.getCategoryName()
        );
    }
}
