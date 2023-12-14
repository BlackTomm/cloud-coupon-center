package org.coding.record.service;

import org.coding.record.LogTag;
import org.coding.record.annotation.TraceId;
import org.springframework.stereotype.Service;

/**
 * Description:
 * Create by blacktom on 2023/12/14
 */
@Service
public class HunterServiceImpl {
    private static final LogTag LOGGER = LogTag.getLogger(HunterServiceImpl.class);

    @TraceId
    public void printInvokeLog() {
        String methodName = "printInvokerLog";
        LOGGER.info(methodName, "in", "start print log");
        LOGGER.info(methodName, "out", "end print log");
    }
}
