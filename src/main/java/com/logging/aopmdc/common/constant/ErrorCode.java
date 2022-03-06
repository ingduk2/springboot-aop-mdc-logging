package com.logging.aopmdc.common.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.function.Predicate;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    OK(0, 200, ErrorCategory.NORMAL, "OK"),

    BAD_REQUEST(10000, 400, ErrorCategory.CLIENT_SIDE, "BAD_REQUEST"),
    SPRING_BAD_REQUEST(10001, 400, ErrorCategory.CLIENT_SIDE, "SPRING_BAD_REQUEST"),

    INTERNAL_ERROR(20000, 500, ErrorCategory.SERVER_SIDE, "INTERNAL_ERROR"),
    SPRING_INTERNAL_ERROR(20001, 501, ErrorCategory.SERVER_SIDE, "SPRING_INTERNAL_ERROR");

    private final int code;
    private final int status;
    private final ErrorCategory errorCategory;
    private final String message;

    public enum ErrorCategory {
        NORMAL,
        CLIENT_SIDE,
        SERVER_SIDE
    }

    public String getMessage(Exception e) {
        return getMessage(e.getMessage());
    }

    public String getMessage(String message) {
        return Optional.ofNullable(message)
                .filter(Predicate.not(String::isBlank))
                .orElse(getMessage());
    }

    public boolean isClientSideError() {
        return this.getErrorCategory() == ErrorCategory.CLIENT_SIDE;
    }

    public boolean isServerSideError() {
        return this.getErrorCategory() == ErrorCategory.SERVER_SIDE;
    }


}
