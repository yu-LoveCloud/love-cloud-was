package com.lovecloud.usermanagement.domain.repository;

import com.lovecloud.usermanagement.domain.User;
import com.lovecloud.usermanagement.domain.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndUserRole(String email, UserRole userRole);
}
