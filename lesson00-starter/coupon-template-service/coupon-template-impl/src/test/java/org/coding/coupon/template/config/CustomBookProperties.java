package org.coding.coupon.template.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Description:
 * Create by blacktom on 2023/11/27
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "custombook")
@PropertySource({"classpath:customBook.properties"})
public class CustomBookProperties {
    private String name;

    private double price;

    private String description;
}
