package com.lovecloud.productmanagement.domain.repository;

import com.lovecloud.productmanagement.domain.Product;
import com.lovecloud.productmanagement.exception.NotFoundProductException;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategoryId(Long categoryId);

    default Product findByIdOrThrow(Long id) {
        return findById(id).orElseThrow(NotFoundProductException::new);
    }
}
