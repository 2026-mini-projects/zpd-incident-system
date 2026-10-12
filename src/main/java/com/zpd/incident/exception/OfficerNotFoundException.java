package com.zpd.incident.exception;

// 최초 배정 요청에 지정된 경찰관 번호가 존재하지 않을 때 발생하는 예외
public class OfficerNotFoundException extends RuntimeException {

    // 조회에 사용한 경찰관 번호를 예외 메시지에 포함한다.
    public OfficerNotFoundException(Integer officerId) {
        super("경찰관을 찾을 수 없습니다. officerId: " + officerId);
    }
}
