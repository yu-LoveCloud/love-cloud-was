package com.lovecloud.productmanagement.domain.repository;

import com.lovecloud.productmanagement.domain.Product;
import com.lovecloud.productmanagement.exception.NotFoundProductException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    default Product findByIdOrThrow(Long id) {
        return findById(id).orElseThrow(NotFoundProductException::new);
    }
}
