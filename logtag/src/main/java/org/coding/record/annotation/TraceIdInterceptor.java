package org.coding.record.annotation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.coding.record.utils.TraceIdUtils;
import org.springframework.stereotype.Component;

/**
 * Description:
 * Create by blacktom on 2023/12/12
 */
@Aspect
@Component
public class TraceIdInterceptor {
    public TraceIdInterceptor() {
    }

    @Around("@annotation(traceId)")
    public Object doInterceptor(ProceedingJoinPoint proceedingJoinPoint, TraceId traceId) throws Throwable {
        TraceIdUtils.before();

        Object result = proceedingJoinPoint.proceed();


    }
}
