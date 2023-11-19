package org.coding.coupon.template.domains.rules;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Description: 批次规则
 * Create by blacktom on 2023/10/29
 */
@Data
@AllArgsConstructor
@Builder
//@JsonRootName(value = "templateRule")
//@JsonIgnoreProperties(ignoreUnknown = true)
public class TemplateRule {
    /**
     * 折扣，存储多个折扣门槛
     */
    private List<Discount> discountInfos;

    private long highDiscount;

    /**
     * 领券数量限制
     */
    private int limitation;

    /**
     * 过期时间
     */
    private long deadline;
}
