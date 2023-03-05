package cmc.hana.umuljeong.exception;

import cmc.hana.umuljeong.exception.common.ErrorCode;
import org.springframework.security.core.AuthenticationException;

public class JwtAuthenticationException extends AuthenticationException {

    public JwtAuthenticationException(ErrorCode errorCode) {
        super(errorCode.name());
    }
}
