package cmc.hana.umuljeong.exception;

import cmc.hana.umuljeong.exception.common.CustomException;
import cmc.hana.umuljeong.exception.common.ErrorCode;

public class TaskCategoryException extends CustomException {
    public TaskCategoryException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public TaskCategoryException(ErrorCode errorCode) {
        super(errorCode);
    }

}
