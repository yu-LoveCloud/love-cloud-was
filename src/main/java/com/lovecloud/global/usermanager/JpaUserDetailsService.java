package com.lovecloud.global.usermanager;

import com.lovecloud.usermanagement.domain.User;
import com.lovecloud.usermanagement.domain.UserRole;
import com.lovecloud.usermanagement.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public SecurityUser loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByEmail(email);

        if(user.isPresent()) {
            return new SecurityUser(user.get());
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }

    public SecurityUser loadUserByUsernameAndRole(String email, UserRole userRole) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmailAndUserRole(email, userRole);

        if (user.isPresent()) {
            return new SecurityUser(user.get());
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}
