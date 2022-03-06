package com.logging.aopmdc.common.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LogConst {
    LOG_ID("tx.id"),
    PROJECT_MAIN_PACKAGE("com.logging.aopmdc");

    private final String value;
}
