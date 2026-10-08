package com.zpd.incident;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class DbConnectionTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void 사용자_테이블_조회() {
        Long count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM zpd_user",
                Long.class
        );

        assertNotNull(count);
        System.out.println("DB 연결 성공! 사용자 수: " + count);
    }
}