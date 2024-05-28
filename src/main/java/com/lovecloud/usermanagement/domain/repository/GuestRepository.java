package com.lovecloud.usermanagement.domain.repository;

import com.lovecloud.usermanagement.domain.Guest;
import com.lovecloud.usermanagement.exception.NotFoundGuestException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<Guest, Long> {

    default Guest findByIdOrThrow(Long id) {
        return findById(id).orElseThrow(NotFoundGuestException::new);
    }
}
