package com.lovecloud.productmanagement.domain.repository;

import com.lovecloud.productmanagement.domain.ProductOptions;
import com.lovecloud.productmanagement.exception.NotFoundProductOptionsException;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductOptionsRepository extends JpaRepository<ProductOptions, Long> {

    List<ProductOptions> findByProductIdAndIsDeleted(Long productId, boolean isDeleted);

    Optional<ProductOptions> findByIdAndIsDeleted(Long id, boolean isDeleted);

    @Query("SELECT p FROM ProductOptions p WHERE p.product.id = :productId AND p.id != :excludeId AND p.isDeleted = false")
    List<ProductOptions> findOthersByProductId(Long productId, Long excludeId);

    default ProductOptions findByIdOrThrow(Long id) {
        return findById(id).orElseThrow(NotFoundProductOptionsException::new);
    }

    default ProductOptions findByIdAndIsDeletedOrThrow(Long id, boolean isDeleted) {
        return findByIdAndIsDeleted(id, isDeleted).orElseThrow(
                NotFoundProductOptionsException::new);
    }
}
