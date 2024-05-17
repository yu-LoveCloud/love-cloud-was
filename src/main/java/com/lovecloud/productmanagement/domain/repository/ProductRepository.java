package com.lovecloud.productmanagement.domain.repository;

import com.lovecloud.productmanagement.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
