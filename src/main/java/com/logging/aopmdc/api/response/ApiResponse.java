package com.logging.aopmdc.api.response;

import com.logging.aopmdc.common.constant.ErrorCode;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class ApiResponse<T> extends ApiErrorResponse {

    private final T data;

    private ApiResponse(T data) {
        super(true, ErrorCode.OK.getStatus(), ErrorCode.OK.getCode(), ErrorCode.OK.getMessage());
        this.data = data;
    }

    public static <T> ApiResponse<T> of(T data) {
        return new ApiResponse<>(data);
    }
}
