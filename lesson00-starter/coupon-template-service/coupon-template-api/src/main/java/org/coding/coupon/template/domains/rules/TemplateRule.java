package org.coding.coupon.template.domains.rules;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Description: 批次规则
 * Create by blacktom on 2023/10/29
 */
@Data
@AllArgsConstructor
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
