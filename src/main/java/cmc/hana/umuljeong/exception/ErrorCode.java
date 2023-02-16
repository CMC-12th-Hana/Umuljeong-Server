package cmc.hana.umuljeong.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    // common
    FORBIDDEN(HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증정보가 유효하지 않습니다."),

    // member
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 사용자가 존재하지 않습니다.");

    private final HttpStatus status;
    private final String message;


    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
