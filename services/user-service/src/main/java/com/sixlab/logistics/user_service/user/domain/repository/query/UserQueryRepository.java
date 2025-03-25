package com.sixlab.logistics.user_service.user.domain.repository.query;

import com.sixlab.logistics.user_service.user.domain.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserQueryRepository {

    Page<User> searchUsers(String keyword, String sort, String order, Pageable pageable);

}
