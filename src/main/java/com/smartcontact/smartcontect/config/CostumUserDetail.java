package com.smartcontact.smartcontect.config;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.smartcontact.smartcontect.entity.User;

public class CostumUserDetail implements UserDetails {

    private User user;


    public CostumUserDetail(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
    
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(user.getRole());
        return List.of(simpleGrantedAuthority);
    }

    @Override
    public String getPassword() {
       
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        
        return user.getEmail();
    }

    @Override
    public boolean isEnabled() {
        return user.isEnable(); // This ensures only enabled users can log in
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

}
