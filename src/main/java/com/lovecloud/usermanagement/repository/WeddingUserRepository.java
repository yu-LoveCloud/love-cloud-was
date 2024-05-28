package com.lovecloud.usermanagement.repository;

import com.lovecloud.usermanagement.domain.WeddingUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeddingUserRepository extends JpaRepository<WeddingUser, Long> {
}
