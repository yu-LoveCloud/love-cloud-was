package com.lovecloud.global.jwt.refresh;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long>{


    Optional<RefreshToken> findByUsername(String email);

    void deleteByUsername(String email);
}
