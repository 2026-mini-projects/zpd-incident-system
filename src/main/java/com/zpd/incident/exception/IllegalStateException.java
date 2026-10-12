package com.zpd.incident.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Officer에 퇴직 경찰관 사건 담당 불가 예외 처리
@ResponseStatus(HttpStatus.CONFLICT)
public class IllegalStateException extends RuntimeException {
    public IllegalStateException(Integer officerId) {
        super("퇴직한 경찰관은 사건을 담당할 수 없습니다. officerId: " + officerId
        );
    }
}
