package com.logging.aopmdc.common.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {

    TIMEZONE_HAS_NOT_ALPHABET_OR_SLASH("timezone has not alphabet or slash"),
    TIMEZONE_EMPTY("timezone empty String");

    private final String message;
}
