package com.logging.aopmdc.common.exception;

import com.logging.aopmdc.common.constant.LogConst;
import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.function.Predicate;

@UtilityClass
public class ExceptionUtil {

    public static StackTraceElement getExceptionLocationInProjectMainPackage(Exception ex) {
        return Arrays.stream(ex.getStackTrace())
                .filter(containsProjectMainPackage)
                .findFirst()
                .orElse(null);
    }

    Predicate<StackTraceElement> containsProjectMainPackage = trace ->
            trace.getClassName().contains(LogConst.PROJECT_MAIN_PACKAGE.getValue());
}
