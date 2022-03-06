package com.logging.aopmdc.common.error;

import com.logging.aopmdc.common.constant.ErrorCode;
import com.logging.aopmdc.api.response.ApiErrorResponse;
import com.logging.aopmdc.common.exception.BaseException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice(annotations = RestController.class)
public class ApiExceptionAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Object> base(BaseException e, WebRequest request) {
        logger.error("custom");
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
    public ResponseEntity<Object> exception(Exception e, WebRequest request) {
        logger.error("Exception");
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
        logger.error("handleExceptionInternal");
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
