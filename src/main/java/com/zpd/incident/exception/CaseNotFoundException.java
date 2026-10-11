package com.zpd.incident.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// 요청한 사건 번호에 해당하는 사건이 없을 때 HTTP 404로 응답하는 예외
@ResponseStatus(HttpStatus.NOT_FOUND)
public class CaseNotFoundException extends RuntimeException {

    // 조회에 사용한 사건 번호를 예외 메시지에 포함
    public CaseNotFoundException(Integer caseId){
        super("사건을 찾을 수 없습니다. caseId: " + caseId);
    }
}
