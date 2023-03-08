package cmc.hana.umuljeong.exception;

import cmc.hana.umuljeong.exception.common.CustomException;
import cmc.hana.umuljeong.exception.common.ErrorCode;

public class MessageException extends CustomException {
    public MessageException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public MessageException(ErrorCode errorCode) {
        super(errorCode);
    }
}
