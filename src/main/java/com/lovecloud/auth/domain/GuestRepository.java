package com.lovecloud.auth.domain;

import com.lovecloud.usermanagement.domain.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import com.lovecloud.auth.exception.NotFoundUserException;

import java.util.Optional;

public interface GuestRepository extends JpaRepository<Guest, Long>{

    boolean existsByEmail(String email);

    default Guest getByEmailOrThrow(String email) {
        return findByEmail(email)
                .orElseThrow(NotFoundUserException::new);
    }

    Optional<Guest> findByEmail(String email);
}
