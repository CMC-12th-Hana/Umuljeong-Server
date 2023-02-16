package cmc.hana.umuljeong.exception;

public class MemberException extends CustomException {
    public MemberException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public MemberException(ErrorCode errorCode) {
        super(errorCode);
    }
}

