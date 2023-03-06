package cmc.hana.umuljeong.exception;

import cmc.hana.umuljeong.exception.common.CustomException;
import cmc.hana.umuljeong.exception.common.ErrorCode;

public class BusinessException extends CustomException {
    public BusinessException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode);
    }
}
