package com.sixlab.logistics.user_service.user.domain.repository;

import com.sixlab.logistics.user_service.user.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User> {

    Optional<User> findByUsername(String username);

    Optional<User> findBySlackId(String slackId);

}
