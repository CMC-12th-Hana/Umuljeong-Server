package cmc.hana.umuljeong.exception;

import cmc.hana.umuljeong.exception.common.CustomException;
import cmc.hana.umuljeong.exception.common.ErrorCode;

public class AuthException extends CustomException {
    public AuthException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public AuthException(ErrorCode errorCode) {
        super(errorCode);
    }
}
