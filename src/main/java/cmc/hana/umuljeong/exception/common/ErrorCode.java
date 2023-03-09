package cmc.hana.umuljeong.exception.common;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    // Common
    FORBIDDEN(HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증정보가 유효하지 않습니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),

    // Auth
    JWT_BAD_REQUEST(HttpStatus.UNAUTHORIZED, "잘못된 JWT 서명입니다."),
    JWT_ACCESS_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "만료된 토큰입니다."),
    JWT_UNSUPPORTED_TOKEN(HttpStatus.UNAUTHORIZED, "지원하지 않는 JWT 토큰입니다."),
    JWT_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "유효한 JWT 토큰이 없습니다."),


    // Member
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 사용자가 존재하지 않습니다."),

    // Task
    TASK_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 업무가 존재하지 않습니다."),
    TASK_ACCESS_DENIED(HttpStatus.FORBIDDEN, "본인 회사의 업무에만 접근할 수 있습니다."),

    // Company
    COMPANY_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 회사가 존재하지 않습니다."),
    COMPANY_ACCESS_DENIED(HttpStatus.FORBIDDEN, "본인 회사의 데이터만 접근할 수 있습니다."),
    COMPANY_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 속한 회사가 있습니다."),

    // Business
    BUSINESS_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 사업이 존재하지 않습니다."),
    BUSINESS_ACCESS_DENIED(HttpStatus.FORBIDDEN, "본인 회사 고객사의 사업이 아닙니다."),

    // ClientCompany
    CLIENT_COMPANY_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 고객사가 존재하지 않습니다."),
    CLIENT_COMPANY_ACCESS_DENIED(HttpStatus.FORBIDDEN, "본인 회사의 고객사가 아닙니다."),

    // TaskCategory
    TASK_CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 업무 카테고리가 존재하지 않습니다."),
    TASK_CATEGORY_ACCESS_DENIED(HttpStatus.FORBIDDEN, "본인 회사의 업무 카테고리가 아닙니다."),

    // Message
    MESSAGE_SEND_FAILED(HttpStatus.BAD_REQUEST, "메시지 전송이 실패했습니다. 올바른 번호인지 확인하세요."),
    MESSAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "인증번호 전송 기록이 존재하지 않습니다."),
    MESSAGE_VERIFICATION_TIMEOUT(HttpStatus.BAD_REQUEST, "인증 번호가 만료되었습니다.");

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
