package org.coding.record;

import org.junit.Test;

/**
 * Description:
 * Create by blacktom on 2023/12/12
 */
public class LogTagTest {
    private static final LogTag LOGGER = LogTag.getLogger(LogTagTest.class);

    @Test
    public void logTrace() {
        LOGGER.info("logTrace", "", "just test logInfo 1");
        LOGGER.info("logTrace", "", "just test logInfo 2");
        LOGGER.info("logTrace", "", "just test logInfo 3");
        LOGGER.info("logTrace", "", "just test logInfo 4");
    }
}
