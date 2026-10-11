package com.zpd.incident.service;

import com.zpd.incident.repository.UserRepository;
import com.zpd.incident.repository.OfficerRepository;
import org.springframework.stereotype.Service;
import com.zpd.incident.dto.response.UserResponse;
import com.zpd.incident.dto.response.UserOfficerResponse;
import com.zpd.incident.entity.User;
import com.zpd.incident.entity.Officer;
import com.zpd.incident.entity.enums.Role;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final OfficerRepository officerRepository;

    public UserService(
            UserRepository userRepository,
            OfficerRepository officerRepository) {
        this.userRepository = userRepository;
        this.officerRepository = officerRepository;
    }

    @Transactional(readOnly = true)
    public UserResponse getMyInfo(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "사용자를 찾을 수 없습니다."
                ));

        UserOfficerResponse officerResponse = null;

        if (user.getRole() == Role.OFFICER) {
            Officer officer = officerRepository.findByUser_Id(userId)
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "경찰관 프로필을 찾을 수 없습니다."
                    ));

            officerResponse = new UserOfficerResponse(
                    officer.getId(),
                    officer.getName(),
                    officer.getSpecies(),
                    officer.getSize(),
                    officer.getDistrict(),
                    officer.getStatus()
            );
        }

        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getNickname(),
                user.getRole(),
                officerResponse
        );
    }
}