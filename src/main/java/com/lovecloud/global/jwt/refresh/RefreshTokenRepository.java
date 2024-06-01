package com.lovecloud.global.jwt.refresh;

import com.lovecloud.usermanagement.domain.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long>{


    Optional<RefreshToken> findByUsernameAndUserRole(String email, UserRole userRole);

    void deleteByUsernameAndUserRole(String email, UserRole userRole);
}
