package com.sixlab.logistics.user_service.user.application.service;

import com.sixlab.logistics.user_service.user.application.dto.Role;
import com.sixlab.logistics.user_service.user.application.dto.SignupRequestDto;
import com.sixlab.logistics.user_service.user.application.dto.SignupResponseDto;
import com.sixlab.logistics.user_service.user.application.dto.UserResponseDto;
import com.sixlab.logistics.user_service.user.domain.model.User;
import com.sixlab.logistics.user_service.user.domain.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.token}")
    private String ADMIN_TOKEN;

    public SignupResponseDto registerUser(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String hashedPassword = passwordEncoder.encode(requestDto.getPassword());

        // 중복된 회원 확인
        Optional<User> checkUser = userRepository.findByUsername(requestDto.getUsername());
        // 에러메시지 자세히 던져도되는가?
        if (checkUser.isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 사용자입니다.");
        }

        // 중복된 slack id 확인
        String slackId = requestDto.getSlackId();
        Optional<User> checkSlackId = userRepository.findBySlackId(slackId);
        if (checkSlackId.isPresent()) {
            throw new IllegalArgumentException("중복된 slack id 입니다.");
        }

        Role role = requestDto.getRole();
        if (role.equals(Role.MASTER) && !ADMIN_TOKEN.equals(requestDto.getAdminToken())) {
            throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가합니다.");
        }

        User user = User.create(username, hashedPassword, slackId, role);
        userRepository.save(user);

        return SignupResponseDto.builder()
                .username(user.getUsername())
                .role(role)
                .build();
    }

    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));
        return UserResponseDto.of(user);
    }
}
