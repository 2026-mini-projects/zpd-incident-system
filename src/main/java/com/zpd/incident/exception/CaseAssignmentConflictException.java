package com.zpd.incident.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// WAITING 상태가 아니거나 이미 담당자가 있는 사건에 최초 배정을 시도하면 HTTP 409로 응답하는 예외
@ResponseStatus(HttpStatus.CONFLICT)
public class CaseAssignmentConflictException extends RuntimeException {

    // 배정에 실패한 사건 번호를 예외 메시지에 포함한다.
    public CaseAssignmentConflictException(Integer caseId) {
        super("현재 상태에서는 담당 경찰관을 배정할 수 없습니다. caseId: " + caseId);
    }
}
