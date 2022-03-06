package com.logging.aopmdc.api.response;

import com.logging.aopmdc.common.constant.ErrorCode;
import lombok.*;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiErrorResponse {

    private final Boolean success;
    private final Integer status;
    private final Integer errorCode;
    private final String message;

    public static ApiErrorResponse of(Boolean success, Integer status, Integer errorCode, String message) {
        return new ApiErrorResponse(
                success,
                status,
                errorCode,
                message
        );
    }

    public static ApiErrorResponse of (Boolean success, ErrorCode errorCode) {
        return new ApiErrorResponse(
                success,
                errorCode.getStatus(),
                errorCode.getCode(),
                errorCode.getMessage()
        );
    }

}
