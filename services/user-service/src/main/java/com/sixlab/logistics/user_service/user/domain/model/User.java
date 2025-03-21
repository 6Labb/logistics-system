package com.sixlab.logistics.user_service.user.domain.model;

import com.sixlab.logistics.common.shared.domain.BasicEntity;
import com.sixlab.logistics.user_service.user.application.dto.Role;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_users", schema = "users")
public class User extends BasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String slackId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    private User(String username, String password, String slackId, Role role) {
        this.username = username;
        this.password = password;
        this.slackId = slackId;
        this.role = role;
    }

    public static User create(String username, String password, String slackId, Role role) {
        return new User(username, password, slackId, role);
    }

    // 엔티티클래스에서 업데이트 메서드 작성하는게 맞을까?
    public void updatedUser(String username, String password, String slackId, String email) {
        this.username = StringUtils.hasText(username) ? username : this.username;
        this.password = StringUtils.hasText(password) ? password : this.password;
        this.slackId = StringUtils.hasText(slackId) ? slackId : this.slackId;
    }


}
