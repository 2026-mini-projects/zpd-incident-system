package com.zpd.incident.dto.response;

import com.zpd.incident.entity.enums.Role;

public class UserResponse {

    private final Integer id;
    private final String username;
    private final String nickname;
    private final Role role;
    private final UserOfficerResponse officer;

    public UserResponse(
            Integer id,
            String username,
            String nickname,
            Role role,
            UserOfficerResponse officer) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
        this.role = role;
        this.officer = officer;
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getNickname() {
        return nickname;
    }

    public Role getRole() {
        return role;
    }

    public UserOfficerResponse getOfficer() {
        return officer;
    }
}