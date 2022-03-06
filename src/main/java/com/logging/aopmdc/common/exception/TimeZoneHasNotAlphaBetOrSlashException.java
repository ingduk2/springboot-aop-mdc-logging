package com.logging.aopmdc.common.exception;

import com.logging.aopmdc.common.constant.ErrorMessage;

public class TimeZoneHasNotAlphaBetOrSlashException extends BaseException {
    public TimeZoneHasNotAlphaBetOrSlashException() {
        super(ErrorMessage.TIMEZONE_HAS_NOT_ALPHABET_OR_SLASH);
    }
}
