package org.coding.coupon.template.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * Description:
 * Create by blacktom on 2023/11/27
 */
@Configuration
@ConfigurationProperties(prefix = "validate")
@PropertySource({"classpath:validConfig.properties"})
@Validated
@Data
public class ValidConfigProperties {
    @NotBlank
    private String hostName;

    @Min(1048)
    @Max(6475)
    private int port;

    @Pattern(regexp = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$")
    private String from;
}
