package com.zpd.incident.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CurrentUserProvider {

    private final Integer userId;

    public CurrentUserProvider(
            @Value("${app.dev-user-id:12}") Integer userId) {
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }
}