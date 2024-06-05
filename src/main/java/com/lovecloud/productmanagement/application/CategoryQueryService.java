package com.lovecloud.productmanagement.application;

import com.lovecloud.productmanagement.domain.repository.CategoryRepository;
import com.lovecloud.productmanagement.query.response.CategoryListResponse;
import com.lovecloud.productmanagement.query.response.CategoryListResponseMapper;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CategoryQueryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryListResponse> findAll() {
        return categoryRepository.findAll().stream()
                .map(CategoryListResponseMapper::map)
                .collect(Collectors.toList());
    }
}
