package com.logging.aopmdc.common.aspect.log.pointcut;

import org.aspectj.lang.annotation.Pointcut;

public class LogPointCut {

    private static final String ANNOTATION_PACKAGE = "com.logging.aopmdc.common.aspect.log.annotation.";

    @Pointcut("@within(" + ANNOTATION_PACKAGE +"LogFull)")
    public void withinLogFullAnnotation() {}

    @Pointcut("@annotation(" + ANNOTATION_PACKAGE + "LogExecutionTime)")
    public void logExecutionTimeAnnotation() {}

    @Pointcut("@within(" + ANNOTATION_PACKAGE + "LogExecutionTime)")
    public void withinLogExecutionTimeAnnotation() {}

    @Pointcut("@annotation(" + ANNOTATION_PACKAGE + "LogReturnValue)")
    public void logReturnValueAnnotation() {}

    @Pointcut("@within(" + ANNOTATION_PACKAGE + "LogReturnValue)")
    public void withinLogReturnValueAnnotation() {}
}
