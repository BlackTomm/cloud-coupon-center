package org.coding.record;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Description:
 * Create by blacktom on 2023/12/11
 */
public class LogTag {
    /**
     * 日志序号拆分
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(LogTag.class);
    private static final InheritableThreadLocal<String> TRACE_ID = new InheritableThreadLocal<>();
    private static final InheritableThreadLocal<AtomicInteger> INDEX = new InheritableThreadLocal<>();

    /**
     * 日志分类
     */
    private static final Logger INFO_LOGGER = LoggerFactory.getLogger("InfoLogger");

    private static final Map<String, LogTag> LOGGER_MANAGER = new ConcurrentHashMap<>();

    /**
     * 日志属性
     */
    private String classPath = "";

    /**
     * 常量
     */
    private static final String UNKNOWN_ATTR = "unknown";
    private static final Integer DEFAULT_INDEX = 0;

    public static LogTag getLogger(Class<?> clazz) {
        return clazz == null ? getLogger(UNKNOWN_ATTR) : getLogger(clazz.getSimpleName());
    }

    public static LogTag getLogger(String path) {
        if (path == null) {
            path = UNKNOWN_ATTR;
        }

        if (!LOGGER_MANAGER.containsKey(path)) {
            LOGGER_MANAGER.put(path, new LogTag(path));
        }

        return LOGGER_MANAGER.get(path);
    }

    private LogTag(String path) {
        this.classPath = path == null ? UNKNOWN_ATTR + " #" : path + " #";
    }

    public void info(String methodName, String useId, Object... logParams) {
        List<String> content = getLoggerContent(logParams);
        for (String con : content) {
            INFO_LOGGER.info(con);
        }

    }

    private static List<String> getLoggerContent(Object... logParams) {
        List<String> content = new ArrayList<>();
        if (logParams == null || logParams.length == 0) {
            content.add(getTraceId());
            return content;
        }

        for (Object logParam : logParams) {
            content.add(getTraceId() + JSON.toJSON(logParam));
        }

        return content;
    }

    private static String getTraceId() {
        int logIndex = INDEX.get().incrementAndGet();
        if (getCurrentTraceId() != null) {
            return "[" + getCurrentTraceId() + "-" + logIndex + "]";
        } else {
            return "[" + Thread.currentThread().getId() + "-" + logIndex + "]";
        }
    }

    public static String getCurrentTraceId() {
        return TRACE_ID.get();
    }

    public static void setTraceId(String tid) {
        TRACE_ID.set(tid);
        INDEX.set(new AtomicInteger(DEFAULT_INDEX));
    }
}
