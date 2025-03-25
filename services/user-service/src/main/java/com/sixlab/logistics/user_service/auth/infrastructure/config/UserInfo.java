package com.sixlab.logistics.user_service.auth.infrastructure.config;


import com.sixlab.logistics.user_service.user.application.dto.Role;

public class UserInfo {

    private final String username;
    private final String password;
    private final Long userId;
    private final Role role;

    public UserInfo(String username,  String password, Long userId, Role role) {
        this.username = "UNUSED";
        this.password = "UNUSED";
        this.userId = userId;
        this.role = role;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public Long getUserId() { return userId; }
    public Role getRole() { return role; }


}
