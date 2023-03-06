package cmc.hana.umuljeong.exception;

import cmc.hana.umuljeong.exception.common.CustomException;
import cmc.hana.umuljeong.exception.common.ErrorCode;

public class ClientCompanyException extends CustomException {
    public ClientCompanyException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public ClientCompanyException(ErrorCode errorCode) {
        super(errorCode);
    }
}
