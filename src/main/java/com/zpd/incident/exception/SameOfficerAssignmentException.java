package com.zpd.incident.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class SameOfficerAssignmentException extends RuntimeException {
    public SameOfficerAssignmentException(Integer caseId, Integer officerId) {
        super(" 현재 담당 경찰관과 동일한 경찰관으로 재배정할 수 없습니다. " +
                "caseId: " + caseId +
                ", officerId: " + officerId);
    }
}
