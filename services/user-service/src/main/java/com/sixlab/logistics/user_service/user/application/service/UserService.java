package com.sixlab.logistics.user_service.user.application.service;


import com.querydsl.core.BooleanBuilder;
import com.sixlab.logistics.common.shared.exception.DuplicateResourceException;
import com.sixlab.logistics.common.shared.exception.InvalidParameterException;
import com.sixlab.logistics.common.shared.exception.OwnershipMismatchException;
import com.sixlab.logistics.common.shared.exception.ResourceNotFoundException;
import com.sixlab.logistics.user_service.auth.infrastructure.config.UserDetailsImpl;

import com.sixlab.logistics.user_service.user.application.dto.*;
import com.sixlab.logistics.user_service.user.domain.model.QUser;
import com.sixlab.logistics.user_service.user.domain.model.User;
import com.sixlab.logistics.user_service.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.token}")
    private String ADMIN_TOKEN;

    @Transactional
    public SignupResponseDto registerUser(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String hashedPassword = passwordEncoder.encode(requestDto.getPassword());

        // 중복된 회원 확인
        Optional<User> checkUser = userRepository.findByUsername(requestDto.getUsername());
        // 에러메시지 자세히 던져도되는가?
        if (checkUser.isPresent()) {
            throw new InvalidParameterException("이미 존재하는 사용자입니다.");
        }

        // 중복된 slack id 확인
        String slackId = requestDto.getSlackId();
        Optional<User> checkSlackId = userRepository.findBySlackId(slackId);
        if (checkSlackId.isPresent()) {
            throw new InvalidParameterException("중복된 slack id 입니다.");
        }

        Role role = requestDto.getRole();
        if (role.equals(Role.MASTER) && !ADMIN_TOKEN.equals(requestDto.getAdminToken())) {
            throw new InvalidParameterException("관리자 암호가 틀려 등록이 불가합니다.");
        }

        User user = User.create(username, hashedPassword, slackId, role);
        userRepository.save(user);

        return SignupResponseDto.builder()
                .username(user.getUsername())
                .role(role)
                .build();
    }

    @Transactional(readOnly = true)
    public UserResponseDto getUserById(Long id, UserDetailsImpl userDetails) {
        Role role = userDetails.getUserInfo().getRole();
        Long loginUserId = userDetails.getUserInfo().getUserId();

        // MASTER가 아니고 && 본인도 아니면 예외
        if (!role.equals(Role.MASTER) && !Objects.equals(id, loginUserId)) {
            throw new OwnershipMismatchException("자신의 정보만 조회할 수 있습니다.");
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("사용자를 찾을 수 없습니다."));

        return UserResponseDto.of(user);
    }


    @Transactional
    public UserUpdateResponseDto updateUser(Long id, UserUpdateRequestDto requestDto, UserDetailsImpl userDetails) {

        // 권한 체크
        Role currentUserRole = userDetails.getUserInfo().getRole();
        Long loginUserId = userDetails.getUserInfo().getUserId();

        if (!currentUserRole.equals(Role.MASTER) && !Objects.equals(id, loginUserId)) {
            throw new OwnershipMismatchException("자신 또는 관리자만 수정할 수 있습니다.");
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("사용자를 찾을 수 없습니다."));

        // slackId 중복 확인
        String newSlackId = requestDto.getSlackId();
        userRepository.findBySlackId(newSlackId).ifPresent(existingUser -> {
            if (!existingUser.getId().equals(user.getId())) {
                throw new InvalidParameterException("중복된 slack id 입니다.");
            }
        });

        // MASTER 권한 부여 시 검증
        if (requestDto.getRole() == Role.MASTER && !ADMIN_TOKEN.equals(requestDto.getAdminToken())) {
            throw new InvalidParameterException("관리자 암호가 틀려 권한 변경이 불가합니다.");
        }

        String updatedPassword = user.getPassword();

        if (requestDto.getPassword() != null && !requestDto.getPassword().isBlank()) {
            updatedPassword = passwordEncoder.encode(requestDto.getPassword());
        }

        // 사용자 정보 수정
        user.update(
                requestDto.getUsername() != null ? requestDto.getUsername() : user.getUsername(),
                updatedPassword,
                requestDto.getSlackId() != null ? requestDto.getSlackId() : user.getSlackId(),
                requestDto.getRole() != null ? requestDto.getRole() : user.getRole()
        );

        return UserUpdateResponseDto.of(user);
    }

    @Transactional
    public void deleteUser(Long id, UserDetailsImpl userDetails) {
        Role role = userDetails.getUserInfo().getRole();
        Long loginUserId = userDetails.getUserId();

        if (!role.equals(Role.MASTER) && !Objects.equals(id, loginUserId)) {
            throw new OwnershipMismatchException("자신 또는 관리자만 삭제할 수 있습니다.");
        }

        User user = userRepository.findById(id)
                .filter(u -> u.getDeletedAt() == null)
                .orElseThrow(() -> new ResourceNotFoundException("삭제할 사용자를 찾을 수 없습니다."));

        user.delete(id);
    }


    public Page<UserResponseDto> searchUsers(UserDetailsImpl userDetails, String keyword, String sortBy, String order, int page, int size) {

        Role role = userDetails.getUserInfo().getRole();
        if (!role.equals(Role.MASTER)) {
            throw new OwnershipMismatchException("관리자만 접근할 수 있습니다.");
        }

        if (size != 10 && size != 30 && size != 50) {
            size = 10;
        }

        Sort.Direction direction = order.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        String sortField = (sortBy == null || sortBy.isBlank()) ? "createdAt" : sortBy;

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));

        QUser user = QUser.user;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(user.deletedAt.isNull());

        if (keyword != null && !keyword.isBlank()) {
            builder.and(user.username.containsIgnoreCase(keyword));
        }

        Page<User> users = userRepository.findAll(builder, pageable);
        return users.map(UserResponseDto::of);
    }





}
