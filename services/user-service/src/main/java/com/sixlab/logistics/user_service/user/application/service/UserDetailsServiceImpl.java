package com.sixlab.logistics.user_service.user.application.service;


import com.sixlab.logistics.user_service.user.domain.model.User;
import com.sixlab.logistics.user_service.user.domain.model.UserDetailsImpl;
import com.sixlab.logistics.user_service.user.domain.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        throw new UnsupportedOperationException("사용되지 않는 메서드입니다. userId를 사용하세요.");
    }

    public UserDetails loadUserById(Long userId) throws UsernameNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException(userId + " not found"));

        if (user.getDeletedAt() != null) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }

        return new UserDetailsImpl(user);
    }
}
