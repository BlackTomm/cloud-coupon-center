package org.coding.coupon.template.converter;

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
        return new ObjectMapper().writeValueAsString(templateRule);
    }

    @SneakyThrows
    @Override
    public TemplateRule convertToEntityAttribute(String templateRule) {
        return new ObjectMapper().readerFor(TemplateRule.class).readValue(templateRule);
    }
}
