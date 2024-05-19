package com.lovecloud.auth.domain;

import com.lovecloud.auth.exception.NotFoundUserException;
import com.lovecloud.usermanagement.domain.WeddingUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WeddingUserRepository extends JpaRepository<WeddingUser, Long>{

    boolean existsByEmail(String email);

    default WeddingUser getByEmail(String email) {
        return findByEmail(email)
                .orElseThrow(NotFoundUserException::new);
    }

    Optional<WeddingUser> findByEmail(String email);
}
