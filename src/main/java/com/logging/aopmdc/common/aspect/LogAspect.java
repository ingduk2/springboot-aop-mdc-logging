package com.logging.aopmdc.common.aspect;

import com.logging.aopmdc.common.constant.LogConst;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.UUID;
import java.util.function.Predicate;

@Slf4j
@Aspect
@Component
public class LogAspect {

    @Pointcut("execution(* com.logging.aopmdc.api.time.controller.*.*(..))")
    public void controllerPointCut() {}

    //Advice
    //Around, Before, AfterReturning, AfterThrowing, After
    @Around("controllerPointCut()")
    public Object executionTimeLog(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        try {
            //@Before
            Object result = joinPoint.proceed();
            //@AfterReturning
            long endTime = System.currentTimeMillis();
            log.info("Tx Time : {} ms", (endTime - startTime));
            return result;
        } catch (Exception e) {
            //@AfterThrowing
            throw e;
        } finally {
            //@After
            log.info("finally");
        }
    }

    @Before("controllerPointCut()")
    public void beforeLogging(JoinPoint joinPoint) {
        MDC.put(LogConst.LOG_ID.getValue(), UUID.randomUUID().toString().substring(0, 8));
        log.info("START TRANSACTION :: {}", MDC.get(LogConst.LOG_ID.getValue()));
    }

    @AfterReturning(pointcut = "controllerPointCut()", returning = "returnValue")
    public void afterReturningLogging(JoinPoint joinPoint, Object returnValue) {
        log.info("END TRANSACTION :: {}", returnValue);
    }

    @AfterThrowing(pointcut = "controllerPointCut()", throwing = "ex")
    public void afterThrowingLogging(JoinPoint joinPoint, Exception ex) {
        log.error("TX EXCEPTION : {}, {}, Location : {}",
                ex.getClass().getSimpleName(),
                ex.getMessage(),
                getExceptionLocation(ex));
    }

    private StackTraceElement getExceptionLocation(Exception ex) {
        return Arrays.stream(ex.getStackTrace())
                .filter(containsProjectMainPackage)
                .findFirst()
                .orElse(null);
    }

    Predicate<StackTraceElement> containsProjectMainPackage = trace ->
            trace.getClassName().contains(LogConst.PROJECT_MAIN_PACKAGE.getValue());


    @After("controllerPointCut()")
    public void afterLogging(JoinPoint joinPoint) {
        MDC.clear();
        log.info("MCD Clear");
    }
}
