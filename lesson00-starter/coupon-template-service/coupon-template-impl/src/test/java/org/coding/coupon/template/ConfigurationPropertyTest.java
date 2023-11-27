package org.coding.coupon.template;

import com.alibaba.fastjson.JSON;
import org.coding.coupon.template.config.ConversionConfigProperties;
import org.coding.coupon.template.config.CustomBookProperties;
import org.coding.coupon.template.config.MailConfigProperties;
import org.coding.coupon.template.config.ValidConfigProperties;
import org.coding.coupon.template.config.domain.ThirdPartyItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

/**
 * Description:
 * Create by blacktom on 2023/11/27
 */
@SpringBootTest
@RunWith(SpringRunner.class)
//1.2 替代 configuration\component注解
//@EnableConfigurationProperties(MailConfigProperties.class)
//1.3 替换后集合属性无法注入
//@ConfigurationPropertiesScan({"org.coding.coupon.template.config"})
//2.1 自定义属性解析器
//@ContextConfiguration(classes = CustomCredentialsConverter.class)
public class ConfigurationPropertyTest {
    @Autowired
    private MailConfigProperties mailConfigProperties;

//    @Autowired
//    private InnerBeanProperties innerBeanProperties;
    @Autowired
    private ThirdPartyItem thirdPartyItem;

    @Autowired
    private ValidConfigProperties validConfigProperties;

    @Autowired
    private ConversionConfigProperties conversionConfigProperties;

    @Autowired
    private CustomBookProperties customBookProperties;

    @Test
    public void mailConfigPropertiesTest() {
        System.out.println("mailConfigProperties = " + JSON.toJSON(mailConfigProperties));
    }

    @Test
    public void whenRegisteringCustomCredentialsConverter_thenCredentialsAreParsed() {
        assertEquals("jayxu", mailConfigProperties.getMailCrendential().getUsername());
        assertEquals("password", mailConfigProperties.getMailCrendential().getPassword());
    }

    @Test
    public void thirdPartyItemTest() {
        System.out.println("innerBeanProperties.thirdPartyItem() = " + thirdPartyItem);
    }

    @Test
    public void validConfigPropertiesTest() {
        System.out.println("validConfigProperties = " + validConfigProperties);
    }

    @Test
    public void  conversionConfigPropertiesTest() {
        System.out.println("conversionConfigProperties = " + JSON.toJSON(conversionConfigProperties));
    }

    @Test
    public void customBookPropertiesTest() {
        System.out.println("customBookProperties = " + JSON.toJSON(customBookProperties));
    }
}
