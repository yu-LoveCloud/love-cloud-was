package com.lovecloud.productmanagement.domain.repository;

import com.lovecloud.productmanagement.domain.ProductOptions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductOptionsRepository extends JpaRepository<ProductOptions, Long> {

}
