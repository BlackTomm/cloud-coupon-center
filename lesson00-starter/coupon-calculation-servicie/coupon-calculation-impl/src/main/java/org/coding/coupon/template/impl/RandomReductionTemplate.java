package org.coding.coupon.template.impl;

import org.coding.coupon.template.AbstractRuleTemplate;
import org.coding.coupon.template.domains.rules.TemplateRule;

import java.util.Random;

/**
 * Description:
 * Create by blacktom on 2023/11/24
 */
public class RandomReductionTemplate extends AbstractRuleTemplate {
    @Override
    protected long calculateCouponOrderPrice(long orderTotalAmount, long shopIdOrderTotalAmount, TemplateRule rule) {
        long hitQuota = Math.min(shopIdOrderTotalAmount, rule.getDiscount());
        return orderTotalAmount - new Random().nextInt((int) hitQuota);
    }
}
