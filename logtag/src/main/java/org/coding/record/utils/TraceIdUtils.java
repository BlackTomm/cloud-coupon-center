package org.coding.record.utils;


import org.coding.record.LogTag;

/**
 * Description:
 * Create by blacktom on 2023/12/12
 */
public class TraceIdUtils {
    public static void before() {
        String traceId = getCurrentTId();
    }

    private static String getCurrentTId() {
        String traceId= LogTag.getCurrentTraceId();
    }
}
