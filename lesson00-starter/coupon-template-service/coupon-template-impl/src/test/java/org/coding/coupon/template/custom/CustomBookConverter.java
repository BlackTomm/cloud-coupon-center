package org.coding.coupon.template.custom;

import org.coding.coupon.template.config.CustomBookProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


/**
 * Description:
 * Create by blacktom on 2023/11/27
 */
@Component
@ConfigurationPropertiesBinding
public class CustomBookConverter implements Converter<String, CustomBookProperties> {
    @Override
    public CustomBookProperties convert(String source) {
        String[] data = source.split(",");
        CustomBookProperties book = new CustomBookProperties();
        book.setName(data[0]);
        book.setPrice(Double.parseDouble(data[1]));
        book.setDescription(data[2]);
        return book;
    }
}
