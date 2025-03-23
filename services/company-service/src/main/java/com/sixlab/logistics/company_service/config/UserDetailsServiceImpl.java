package com.sixlab.logistics.company_service.config;


import com.sixlab.logistics.company_service.application.client.UsersClient;
import com.sixlab.logistics.company_service.application.dto.UserResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    private final UsersClient userServiceClient;

    public UserDetailsServiceImpl(UsersClient userServiceClient) {
        this.userServiceClient = userServiceClient;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        throw new UnsupportedOperationException("사용되지 않는 메서드입니다. userId를 사용하세요.");
    }

    public UserDetails loadUserById(Long userId) throws UsernameNotFoundException {
        UserResponse user = userServiceClient.getUserById(userId);

        if (user == null) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }

        return new UserDetailsImpl(user);
    }
}
