package org.coding.coupon.template.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DataSizeUnit;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * Description:
 * Create by blacktom on 2023/11/27
 */
@Configuration
@ConfigurationProperties(prefix = "conversion")
@PropertySource({"classpath:conversionConfig.properties"})
@Data
public class ConversionConfigProperties {
    private Duration defaultTime;
    private Duration nanoTime;

    @DurationUnit(ChronoUnit.HOURS)
    private Duration hourTime;

    @DataSizeUnit(DataUnit.KILOBYTES)
    private DataSize defaultDataSize;

    private CustomBookProperties customBookProperties;
}
