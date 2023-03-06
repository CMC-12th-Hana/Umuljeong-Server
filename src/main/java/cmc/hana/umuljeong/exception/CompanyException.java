package cmc.hana.umuljeong.exception;

import cmc.hana.umuljeong.exception.common.CustomException;
import cmc.hana.umuljeong.exception.common.ErrorCode;

public class CompanyException extends CustomException {
    public CompanyException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public CompanyException(ErrorCode errorCode) {
        super(errorCode);
    }
}
