package com.lovecloud.global.usermanager;

import com.lovecloud.usermanagement.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
public class SecurityUser implements UserDetails {

    private final User user;

    public SecurityUser(User user) {this.user = user;}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> authorities = new ArrayList<>(2);
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getUserRole().name()));

        return authorities;
    }

    //TODO: 보안 점검 후 구현 예정
    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    //TODO: 이하 메서드 User 엔티티 메서드 구현 후 수정 예정
    /*
    계정의 만료 여부 리턴
   */
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    /*
    계정의 잠김 여부 리턴
    */
    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
