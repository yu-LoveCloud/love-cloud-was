package com.lovecloud.productmanagement.domain.repository;

import com.lovecloud.productmanagement.domain.DescriptionImage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DescriptionImageRepository extends JpaRepository<DescriptionImage, Long> {

    List<DescriptionImage> findByProductId(Long productId);
}
