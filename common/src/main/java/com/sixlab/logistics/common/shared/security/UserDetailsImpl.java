package com.sixlab.logistics.common.shared.security;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 *  security 에서 사용할 유저 객체 생성
 *  SecurityContextHolder.getContext().getAuthentication().getPrincipal(); = @AuthorizationPrinciple
 */
public class UserDetailsImpl implements UserDetails {

    private final UserInfo userInfo;

    public UserDetailsImpl(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    // accessing user information from a controller or service
    public UserInfo getUserInfo() { return this.userInfo; }

    // return authority information
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + userInfo.getRole());

        return Collections.singletonList(authority);
    }

    @Override
    public String getUsername() {
        return userInfo.getUsername();
    }

    @Override
    public String getPassword() {
        return userInfo.getPassword();
    }

    public Long getUserId() {
        return userInfo.getUserId();
    }

    @Override public boolean isAccountNonExpired() { return true; }

    @Override public boolean isAccountNonLocked() { return true; }

    @Override public boolean isCredentialsNonExpired() { return true; }

    @Override public boolean isEnabled() { return true; }

}
