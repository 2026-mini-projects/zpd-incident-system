package com.zpd.incident.exception;

import com.zpd.incident.entity.enums.CaseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class InvalidCaseStatusTransitionException extends RuntimeException {
    public InvalidCaseStatusTransitionException(
            Integer caseId,
            CaseStatus currentStatus,
            CaseStatus requestedStatus
    ) {
        super("허용되지 않는 사건 상태 변경입니다. " +
                "caseId: " + caseId +
                ", currentStatus: " + currentStatus +
                ", requestedStatus: " + requestedStatus
        );
    }
}
