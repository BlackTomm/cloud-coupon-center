package org.coding.record;

import org.coding.record.annotation.TraceId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Description:
 * Create by blacktom on 2023/12/12
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class LogTagTest {
    private static final LogTag LOGGER = LogTag.getLogger(LogTagTest.class);

    @Test
    public void testLog1() {
        logTrace();
    }

    @TraceId
    public void logTrace() {
        LOGGER.info("logTrace", "", "just test logInfo 1");
        LOGGER.info("logTrace", "", "just test logInfo 2");
        LOGGER.info("logTrace", "", "just test logInfo 3");
        LOGGER.info("logTrace", "", "just test logInfo 4");
    }

}
