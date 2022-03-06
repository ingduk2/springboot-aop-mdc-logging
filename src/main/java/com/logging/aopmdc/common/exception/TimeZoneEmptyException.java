package com.logging.aopmdc.common.exception;

import com.logging.aopmdc.common.constant.ErrorMessage;

public class TimeZoneEmptyException extends BaseException {
    public TimeZoneEmptyException() {
        super(ErrorMessage.TIMEZONE_EMPTY);
    }
}
