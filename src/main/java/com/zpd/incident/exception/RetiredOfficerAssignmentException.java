package com.zpd.incident.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// 퇴직 상태인 경찰관에게 사건을 배정하려 할 때 HTTP 409로 응답하는 예외
@ResponseStatus(HttpStatus.CONFLICT)
public class RetiredOfficerAssignmentException extends RuntimeException {

    // 배정이 거부된 퇴직 경찰관 번호를 예외 메시지에 포함한다.
    public RetiredOfficerAssignmentException(Integer officerId) {
        super("퇴직한 경찰관은 사건을 담당할 수 없습니다. officerId: " + officerId);
    }
}
