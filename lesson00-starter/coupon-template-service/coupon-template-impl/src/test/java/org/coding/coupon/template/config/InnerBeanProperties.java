package org.coding.coupon.template.config;

import org.coding.coupon.template.config.domain.ThirdPartyItem;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Description:内部 bean注入属性
 * Create by blacktom on 2023/11/27
 */
@Configuration
@PropertySource({"classpath:innerBean.properties"})
public class InnerBeanProperties {
    /**
     * 为什么 prefix需要小写
     */
    @Bean
    @ConfigurationProperties(prefix = "thirdpartyitem")
    public ThirdPartyItem thirdPartyItem() {
        return new ThirdPartyItem();
    }
}
