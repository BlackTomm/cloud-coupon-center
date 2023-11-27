package org.coding.config;

import org.coding.config.domain.MailConfigProperties;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Description:
 * Create by blacktom on 2023/11/27
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ConfigurationPropertyTest {
    @Autowired
    private MailConfigProperties mailConfigProperties;

    @Test
    public void mailConfigPropertiesTest() {
        System.out.println("mailConfigProperties = " + mailConfigProperties);
    }
}
