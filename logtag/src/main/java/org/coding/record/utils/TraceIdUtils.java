package org.coding.record.utils;


import org.coding.record.LogTag;
import org.springframework.util.StringUtils;

import java.util.UUID;

/**
 * Description:
 * Create by blacktom on 2023/12/12
 */
public class TraceIdUtils {
    public static void before() {
        String traceId = getCurrentTraceId();
        LogTag.setTraceId(traceId);
    }

    private static String getCurrentTraceId() {
        String traceId= LogTag.getCurrentTraceId();
        if (!StringUtils.hasText(traceId)) {
            traceId = UUID.randomUUID().toString();
        }
        return traceId;
    }

    public static void after() {
        LogTag.resetTraceId();
    }
}
