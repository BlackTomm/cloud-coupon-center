package org.coding.coupon.template.impl;

import org.coding.coupon.template.AbstractRuleTemplate;
import org.coding.coupon.template.domains.rules.TemplateRule;

/**
 * Description:
 * Create by blacktom on 2023/11/24
 */
public class AntiPuaTemplate extends AbstractRuleTemplate {

    /**
     * 易客诉
     */
    @Override
    protected long calculateCouponOrderPrice(long orderTotalAmount, long shopIdOrderTotalAmount, TemplateRule rule) {
        return orderTotalAmount * 996;
    }
}
