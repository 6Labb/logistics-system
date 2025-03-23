package com.sixlab.logistics.company_service.config;

import com.sixlab.logistics.company_service.application.dto.UserResponse;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;


/**
 *  security 에서 사용할 유저 객체 생성
 *  SecurityContextHolder.getContext().getAuthentication().getPrincipal(); = @AuthorizationPrinciple
 */
public class UserDetailsImpl implements UserDetails {

    private final UserResponse user;

    public UserDetailsImpl(UserResponse user) {
        this.user = user;
    }

    // 사용자의 정보를 가져오는 메서드
    public UserResponse getUser() {
        return this.user;
    }

    // 유저의 권한을 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String authority = user.getRole(); // UserResponse에서 role 가져오기
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority);

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(simpleGrantedAuthority);

        return authorities;
    }

    @Override
    public String getUsername() {
        return user.getUsername(); // FeignClient로 가져온 user의 username 사용
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override public boolean isAccountNonExpired() { return true; }

    @Override public boolean isAccountNonLocked() { return true; }

    @Override public boolean isCredentialsNonExpired() { return true; }

    @Override public boolean isEnabled() { return true; }
}
