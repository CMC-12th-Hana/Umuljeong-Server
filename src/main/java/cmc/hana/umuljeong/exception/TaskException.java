package cmc.hana.umuljeong.exception;

import cmc.hana.umuljeong.exception.common.CustomException;
import cmc.hana.umuljeong.exception.common.ErrorCode;

public class TaskException extends CustomException {

    public TaskException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public TaskException(ErrorCode errorCode) {
        super(errorCode);
    }
}
