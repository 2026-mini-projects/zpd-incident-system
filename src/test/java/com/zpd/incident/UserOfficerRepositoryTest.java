package com.zpd.incident;

import com.zpd.incident.entity.Officer;
import com.zpd.incident.entity.User;
import com.zpd.incident.repository.OfficerRepository;
import com.zpd.incident.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserOfficerRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OfficerRepository officerRepository;

    @Test
    void 로그인_아이디로_사용자_조회() {
        User user = userRepository.findByUsername("officer01")
                .orElseThrow(() ->
                        new AssertionError("officer01 사용자를 찾을 수 없습니다."));

        assertEquals(Integer.valueOf(2), user.getId());
        assertEquals("officer01", user.getUsername());
    }

    @Test
    void 사용자_ID로_경찰관_조회() {
        Officer officer = officerRepository.findByUser_Id(2)
                .orElseThrow(() ->
                        new AssertionError("사용자 ID 2의 경찰관을 찾을 수 없습니다."));

        assertEquals(Integer.valueOf(1001), officer.getId());
        assertEquals("주디 홉스", officer.getName());
    }
}

