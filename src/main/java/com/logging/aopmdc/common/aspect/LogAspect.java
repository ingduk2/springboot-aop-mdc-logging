package com.logging.aopmdc.common.aspect;

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

    @Pointcut("execution(* com.logging.aopmdc.api.time.controller.*.*(..))")
    public void controllerPointCut() {}

    @Pointcut("execution(* com.logging.aopmdc.common.error.ApiExceptionAdvice.*(..))")
    public void apiExceptionAdvice() {}

    @Around("controllerPointCut()")
    public Object executionTimeLog(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
            Object result = joinPoint.proceed();
            long endTime = System.currentTimeMillis();
            log.info("[Time] Tx Time : {} ms, Tx End", (endTime - startTime));
            return result;
    }

    @Before("controllerPointCut()")
    public void beforeLogging(JoinPoint joinPoint) {
        MDC.put(LogConst.LOG_ID.getValue(), UUID.randomUUID().toString().substring(0, 8));
        log.info("[Before] START TRANSACTION :: {}", MDC.get(LogConst.LOG_ID.getValue()));
    }

    @AfterReturning(pointcut = "controllerPointCut() || apiExceptionAdvice()", returning = "returnValue")
    public void afterReturningLogging(JoinPoint joinPoint, Object returnValue) {
        log.info("[AfterReturning] END TRANSACTION :: {}", returnValue);
    }

    @AfterThrowing(pointcut = "controllerPointCut()", throwing = "ex")
    public void afterThrowingLogging(JoinPoint joinPoint, Exception ex) {
        log.error("[AfterThrowing] TX EXCEPTION : {}, {}, Location : {}",
                ex.getClass().getSimpleName(),
                ex.getMessage(),
                ExceptionUtil.getExceptionLocationInProjectMainPackage(ex));
    }

    /**
     * AfterThrowing or AfterReturning 후에 동작
     * @param joinPoint
     */
    @After("controllerPointCut()")
    public void afterLogging(JoinPoint joinPoint) {
        log.info("[After]");
//        MDC.clear();
    }
}
