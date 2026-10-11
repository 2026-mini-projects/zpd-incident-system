package com.zpd.incident.controller;

import com.zpd.incident.common.CurrentUserProvider;
import com.zpd.incident.dto.response.UserResponse;
import com.zpd.incident.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final CurrentUserProvider currentUserProvider;

    public UserController(
            UserService userService,
            CurrentUserProvider currentUserProvider) {
        this.userService = userService;
        this.currentUserProvider = currentUserProvider;
    }

    @GetMapping("/me")
    public Map<String, UserResponse> getMyInfo() {
        UserResponse response =
                userService.getMyInfo(currentUserProvider.getUserId());

        return Map.of("data", response);
    }
}