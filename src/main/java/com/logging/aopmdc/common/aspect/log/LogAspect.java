package com.logging.aopmdc.common.aspect.log;

import com.logging.aopmdc.common.constant.LogConst;
import com.logging.aopmdc.common.exception.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Aspect
@Component
public class LogAspect {

    private static final String LOG_POINT_CUT = "com.logging.aopmdc.common.aspect.log.pointcut.LogPointCut.";
    private static final String WITHIN_LOG_FULL = LOG_POINT_CUT + "withinLogFullAnnotation()";
    private static final String WITHIN_LOG_EXECUTION_TIME = LOG_POINT_CUT + "withinLogExecutionTimeAnnotation()";
    private static final String ANNOTATION_LOG_EXECUTION_TIME = LOG_POINT_CUT + "logExecutionTimeAnnotation()";
    private static final String WITHIN_LOG_RETURN_VALUE = LOG_POINT_CUT + "withinLogReturnValueAnnotation()";
    private static final String ANNOTATION_LOG_RETURN_VALUE = LOG_POINT_CUT + "logReturnValueAnnotation()";

    @Around(WITHIN_LOG_FULL + "||" + WITHIN_LOG_EXECUTION_TIME + "||" + ANNOTATION_LOG_EXECUTION_TIME)
    public Object executionTimeLog(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
            Object result = joinPoint.proceed();
            long endTime = System.currentTimeMillis();
            log.info("[Time] {} Time : {} ms, End", joinPoint.getSignature(), (endTime - startTime));
            return result;
    }

    @AfterReturning(
            pointcut = WITHIN_LOG_FULL + "||" + WITHIN_LOG_RETURN_VALUE + "||" + ANNOTATION_LOG_RETURN_VALUE,
            returning = "returnValue")
    public void afterReturningLogging(JoinPoint joinPoint, Object returnValue) {
        log.info("[AfterReturning] END TRANSACTION :: {}", returnValue);
    }

    @AfterThrowing(pointcut = WITHIN_LOG_FULL, throwing = "ex")
    public void afterThrowingLogging(JoinPoint joinPoint, Exception ex) {
        log.error("[AfterThrowing] TX EXCEPTION : {}, {}, Location : {}",
                ex.getClass().getSimpleName(),
                ex.getMessage(),
                ExceptionUtil.getExceptionLocationInProjectMainPackage(ex));
    }

}
