package org.coding.coupon.template.custom;

import org.coding.coupon.template.config.domain.MailCrendential;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


/**
 * Description: 自定义属性转换器
 * Create by blacktom on 2023/11/27
 */
@Component
@ConfigurationPropertiesBinding
public class CustomCredentialsConverter implements Converter<String, MailCrendential> {

    @Override
    public MailCrendential convert(String source) {
        String[] data = source.split(",");
        return new MailCrendential(data[0], data[1]);
    }
}
