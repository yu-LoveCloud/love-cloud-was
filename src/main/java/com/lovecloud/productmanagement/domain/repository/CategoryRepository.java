package com.lovecloud.productmanagement.domain.repository;

import com.lovecloud.productmanagement.domain.Category;
import com.lovecloud.productmanagement.exception.NotFoundCategoryException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    default Category findByIdOrThrow(Long id) {
        return findById(id).orElseThrow(NotFoundCategoryException::new);
    }
}
