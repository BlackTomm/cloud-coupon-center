package org.coding.coupon.template.converter;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.coding.coupon.template.domains.rules.TemplateRule;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Description:
 * Create by blacktom on 2023/10/29
 * jackson 使用 https://www.baeldung.com/jackson-annotations
 */
@Converter
public class RuleConverter implements AttributeConverter<TemplateRule, String> {
    @SneakyThrows
    @Override
    public String convertToDatabaseColumn(TemplateRule templateRule) {
        return JSON.toJSONString(templateRule);
//        return new ObjectMapper().writer().writeValueAsString(templateRule);
    }

    @SneakyThrows
    @Override
    public TemplateRule convertToEntityAttribute(String templateRule) {
        return JSON.parseObject(templateRule, TemplateRule.class);
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.enable(DeserializationFeature.UNWRAP_ROOT_VALUE);
//        return objectMapper.reader().forType(TemplateRule.class).readValue(templateRule);
    }
}
