package com.lovecloud.global.usermanager;

import com.lovecloud.usermanagement.domain.AccountStatus;
import com.lovecloud.usermanagement.domain.Guest;
import com.lovecloud.usermanagement.domain.User;
import com.lovecloud.usermanagement.domain.WeddingUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
public record SecurityUser(User user) implements UserDetails {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> authorities = new ArrayList<>(2);
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getUserRole().name()));

        return authorities;
    }


    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public String getPassword() { return null; }

    /*
    계정의 만료 여부 리턴
   */
    @Override
    public boolean isAccountNonExpired() { return false; }

    /*
    계정의 잠김 여부 리턴
    */
    @Override
    public boolean isAccountNonLocked() {

        if (user instanceof WeddingUser) {
            return ((WeddingUser) user).getAccountStatus() != AccountStatus.LOCKED;
        } else if (user instanceof Guest) {
            return true;
        }
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {

        if (user instanceof WeddingUser) {
            return ((WeddingUser) user).getAccountStatus() == AccountStatus.ACTIVE;
        } else if (user instanceof Guest) {
            return true;
        }
        return true;
    }
}
