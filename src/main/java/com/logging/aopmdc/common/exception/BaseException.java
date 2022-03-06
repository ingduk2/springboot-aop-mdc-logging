package com.logging.aopmdc.common.exception;

import com.logging.aopmdc.common.constant.ErrorCode;
import com.logging.aopmdc.common.constant.ErrorMessage;
import lombok.Getter;

@Getter
public class BaseException extends RuntimeException{
    private final ErrorCode errorCode;

    public BaseException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
        this.errorCode = ErrorCode.INTERNAL_ERROR;
    }

}
