package org.coding.coupon.template.impl;

import org.coding.coupon.template.AbstractRuleTemplate;
import org.coding.coupon.template.domains.rules.TemplateRule;

/**
 * Description: 未知券，不优惠
 * Create by blacktom on 2023/11/24
 */
public class DummyTemplate extends AbstractRuleTemplate {
    @Override
    protected long calculateCouponOrderPrice(long orderTotalAmount, long shopIdOrderTotalAmount, TemplateRule rule) {
        return orderTotalAmount;
    }
}
