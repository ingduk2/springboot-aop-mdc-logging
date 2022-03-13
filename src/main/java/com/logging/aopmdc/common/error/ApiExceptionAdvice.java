package com.logging.aopmdc.common.error;

import com.logging.aopmdc.common.aspect.log.annotation.LogExecutionTime;
import com.logging.aopmdc.common.aspect.log.annotation.LogReturnValue;
import com.logging.aopmdc.common.constant.ErrorCode;
import com.logging.aopmdc.api.response.ApiErrorResponse;
import com.logging.aopmdc.common.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@Slf4j
@LogReturnValue
@LogExecutionTime // 이게 Method Level 에서는 안먹네.. 흠 aop 안에 내부 호출로 되나..?
@RestControllerAdvice(annotations = RestController.class)
public class ApiExceptionAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Object> base(BaseException e, WebRequest request) {
        log.error("base");
        ErrorCode errorCode = e.getErrorCode();
        HttpStatus httpStatus = errorCode.isClientSideError() ?
                HttpStatus.BAD_REQUEST :
                HttpStatus.INTERNAL_SERVER_ERROR;

        return super.handleExceptionInternal(
            e,
            ApiErrorResponse.of(
                    false,
                    errorCode.getStatus(),
                    errorCode.getCode(),
                    errorCode.getMessage(e)
            ),
            HttpHeaders.EMPTY,
            httpStatus,
            request
        );
    }

    @ExceptionHandler
    public ResponseEntity<Object> constraintViolationException(ConstraintViolationException e, WebRequest request) {
        log.error("constraintViolationException");
        return super.handleExceptionInternal(
                e,
                ApiErrorResponse.of(
                        false,
                        ErrorCode.SPRING_BAD_REQUEST.getStatus(),
                        ErrorCode.SPRING_BAD_REQUEST.getCode(),
                        ErrorCode.SPRING_BAD_REQUEST.getMessage(e)
                ),
                HttpHeaders.EMPTY,
                HttpStatus.BAD_REQUEST,
                request
        );
    }

    @ExceptionHandler
    public ResponseEntity<Object> exception(Exception e, WebRequest request) {
        log.error("exception");
        return super.handleExceptionInternal(
                e,
                ApiErrorResponse.of(
                        false,
                        ErrorCode.INTERNAL_ERROR.getStatus(),
                        ErrorCode.INTERNAL_ERROR.getCode(),
                        ErrorCode.INTERNAL_ERROR.getMessage(e)
                ),
                HttpHeaders.EMPTY,
                HttpStatus.INTERNAL_SERVER_ERROR,
                request
        );
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("handleExceptionInternal");
        ErrorCode errorCode = status.is4xxClientError() ?
                ErrorCode.SPRING_BAD_REQUEST :
                ErrorCode.SPRING_INTERNAL_ERROR;

        return super.handleExceptionInternal(
                ex,
                ApiErrorResponse.of(
                        false,
                        errorCode.getStatus(),
                        errorCode.getCode(),
                        errorCode.getMessage(ex)
                ),
                headers,
                status,
                request
        );
    }
}
