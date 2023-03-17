package cmc.hana.umuljeong.exception.handler;

import cmc.hana.umuljeong.exception.JwtAuthenticationException;
import cmc.hana.umuljeong.exception.common.ApiErrorResult;
import cmc.hana.umuljeong.exception.common.CustomException;
import cmc.hana.umuljeong.exception.common.ErrorCode;
import cmc.hana.umuljeong.web.controller.AuthRestController;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalRestControllerExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ApiErrorResult> conversionFailureHandler(ConversionFailedException ex) {

        String message = ex.getValue() + "는 " + ex.getTargetType() + "으로 변환할 수 없습니다.";
        String cause = ex.getClass().getName();

        return ResponseEntity.badRequest()
                .body(ApiErrorResult.builder().message(message).cause(cause).build());
    }

    @ExceptionHandler
    public ResponseEntity<ApiErrorResult> MethodArgumentTypeHandler(MethodArgumentTypeMismatchException ex) {

        String message = "파라미터가 " + ex.getParameter().getParameterType() + " 타입이 아닙니다.";
        String cause = ex.getClass().getName();

        return ResponseEntity.badRequest()
                .body(ApiErrorResult.builder().message(message).cause(cause).build());
    }


    @ExceptionHandler
    public ResponseEntity<ApiErrorResult> handleExpiredJwtException(JwtAuthenticationException ex) {
        ErrorCode errorCode = ErrorCode.valueOf(ex.getMessage());
        return ResponseEntity.status(errorCode.getStatus())
                .body(ApiErrorResult.builder()
                        .errorCode(errorCode)
                        .message(errorCode.getMessage())
                        .cause(AuthRestController.class.getName())
                        .build());
    }

    @ExceptionHandler // PathVariable 검증 예외
    public ResponseEntity<ApiErrorResult> ConstraintViolationExceptionHandler(ConstraintViolationException e) {
        ConstraintViolation constraintViolation = e.getConstraintViolations().stream().collect(Collectors.toList()).get(0);
        String errorCodeString = constraintViolation.getMessageTemplate();
        ErrorCode errorCode = ErrorCode.valueOf(errorCodeString);
        String cause = e.getClass().getName();
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiErrorResult.builder().errorCode(errorCode).message(errorCode.getMessage()).cause(cause).build());
    }

    @ExceptionHandler // 중복 휴대폰 번호 예외
    public ResponseEntity<ApiErrorResult> HibernateConstraintViolationExceptionHandler(org.hibernate.exception.ConstraintViolationException e) {

        ErrorCode errorCode = ErrorCode.PHONE_NUMBER_ALREADY_EXISTS;
        String cause = e.getClass().getName();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiErrorResult.builder().errorCode(errorCode).message(errorCode.getMessage()).cause(cause).build());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
        String message = getMessage(allErrors.iterator());
        String cause = ex.getClass().getName();
        return ResponseEntity.badRequest()
                .body(ApiErrorResult.builder().errorCode(ErrorCode.BAD_REQUEST).message(message).cause(cause).build());
    }

    private String getMessage(Iterator<ObjectError> errorIterator) {
        final StringBuilder resultMessageBuilder = new StringBuilder();
        while (errorIterator.hasNext()) {
            ObjectError error = errorIterator.next();
            resultMessageBuilder
                    .append("['")
                    .append(((FieldError) error).getField()) // 유효성 검사가 실패한 속성
                    .append("' is '")
                    .append(((FieldError) error).getRejectedValue()) // 유효하지 않은 값
                    .append("' :: ")
                    .append(error.getDefaultMessage()) // 유효성 검사 실패 시 메시지
                    .append("]");

            if (errorIterator.hasNext()) {
                resultMessageBuilder.append("\n");
            }
        }

        return resultMessageBuilder.toString();
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String message = ex.getMessage();
        String cause = ex.getClass().getName();
        ApiErrorResult apiErrorResult = ApiErrorResult.builder().message(message).cause(cause).build();
        return super.handleExceptionInternal(ex, apiErrorResult, headers, status, request);
    }

    @ExceptionHandler(CustomException.class)
    public <T extends ApiErrorResult> ResponseEntity<T> customExceptionHandler(CustomException ex, WebRequest request) {

        ApiErrorResult apiErrorResult = ApiErrorResult.builder()
                .errorCode(ex.getErrorCode())
                .message(ex.getMessage())
                .cause(ex.getClass().getName())
                .build();
        ResponseEntity response = super.handleExceptionInternal(ex, apiErrorResult, new HttpHeaders(), ex.getErrorCode().getStatus(), request);
        return response;
    }

    @ExceptionHandler
    public ResponseEntity<ApiErrorResult> duplicatedEntryExceptionHandler(SQLIntegrityConstraintViolationException ex) {
        return ResponseEntity.status(ex.getErrorCode())
                .body(ApiErrorResult.builder().message("중복된 값입니다.").cause(ex.getClass().toString()).build());
    }

    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<ApiErrorResult> fileSizeLimitExceptionHandler(MultipartException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiErrorResult.builder().message("파일 처리에 실패했습니다.").cause(ex.getClass().toString()).build());

    }

    @ExceptionHandler(Exception.class)
    public <T extends ApiErrorResult> ResponseEntity<T> generalExceptionHandler(Exception exception, WebRequest request) {
        ResponseEntity response = handleExceptionInternal(exception, null, null, HttpStatus.INTERNAL_SERVER_ERROR, request);
        return response;
    }
}
