package com.zpd.incident.exception;

public class CaseReportForbiddenException extends RuntimeException {
    public CaseReportForbiddenException() {
        super("시민만 사건을 신고할 수 있습니다.");
    }
}
