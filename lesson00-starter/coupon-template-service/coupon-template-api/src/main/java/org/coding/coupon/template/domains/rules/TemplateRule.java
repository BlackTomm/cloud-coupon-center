package org.coding.coupon.template.domains.rules;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Description: 批次规则
 * Create by blacktom on 2023/10/29
 */
@Data
@AllArgsConstructor
@Builder
public class TemplateRule {
    /**
     * 优惠门槛
     */
    private long quota;

    private long discount;

    /**
     * 折扣，存储多个折扣门槛
     */
    private List<DiscountItemVO> discountInfos;

    /**
     * 折扣最大优惠金额
     */
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
