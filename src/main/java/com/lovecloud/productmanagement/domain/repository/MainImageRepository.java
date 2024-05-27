package com.lovecloud.productmanagement.domain.repository;

import com.lovecloud.productmanagement.domain.MainImage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MainImageRepository extends JpaRepository<MainImage, Long> {

    List<MainImage> findByProductOptionsId(Long productOptionsId);
}
