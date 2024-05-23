package com.lovecloud.productmanagement.domain.repository;

import com.lovecloud.productmanagement.domain.ProductOptions;
import com.lovecloud.productmanagement.exception.NotFoundProductOptionsException;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductOptionsRepository extends JpaRepository<ProductOptions, Long> {

    List<ProductOptions> findByProductId(Long productId);

    default ProductOptions findByIdOrThrow(Long id) {
        return findById(id).orElseThrow(NotFoundProductOptionsException::new);
    }
}
