package com.lovecloud.productmanagement.domain.repository;

import com.lovecloud.productmanagement.domain.ProductOptions;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductOptionsRepository extends JpaRepository<ProductOptions, Long> {

    List<ProductOptions> findByProductId(Long productId);
}
