package org.coding.record;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Description:
 * Create by blacktom on 2023/12/11
 */
public class Slf4jExample {

    private static Logger LOGGER = LoggerFactory.getLogger(Slf4jExample.class);


    @Test
    public void originalLoggerTest() {
        LOGGER.debug("Debug log message");
        LOGGER.info("Info log message");
        LOGGER.error("Error log message");
    }
}
