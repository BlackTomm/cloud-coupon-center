package org.coding.coupon.template.impl;

import lombok.NoArgsConstructor;
import org.coding.coupon.template.AbstractRuleTemplate;
import org.coding.coupon.template.domains.rules.TemplateRule;

/**
 * Description: 满减券
 * Create by blacktom on 2023/11/24
 */
public class MoneyOffTemplate extends AbstractRuleTemplate {
    @Override
    protected long calculateCouponOrderPrice(long orderTotalAmount, long shopIdOrderTotalAmount, TemplateRule rule) {
        return orderTotalAmount  - Math.min(shopIdOrderTotalAmount, rule.getDiscount());
    }
}
