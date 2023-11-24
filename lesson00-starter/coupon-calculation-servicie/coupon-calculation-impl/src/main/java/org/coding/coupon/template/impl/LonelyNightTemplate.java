package org.coding.coupon.template.impl;

import org.coding.coupon.template.AbstractRuleTemplate;
import org.coding.coupon.template.domains.rules.TemplateRule;

import java.util.Calendar;

/**
 * Description:在这个寂寞的夜晚，你需要金钱的陪伴 午夜10点到次日凌晨2点之间下单，优惠金额翻倍
 * Create by blacktom on 2023/11/24
 */
public class LonelyNightTemplate extends AbstractRuleTemplate {
    @Override
    protected long calculateCouponOrderPrice(long orderTotalAmount, long shopIdOrderTotalAmount, TemplateRule rule) {
        long discount = rule.getDiscount();

        Calendar calendar = Calendar.getInstance();
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        if (hourOfDay == 23 || hourOfDay < 2) {
            discount *=2;
        }

        long couponDiscount = Math.min(shopIdOrderTotalAmount, discount);
        return orderTotalAmount - couponDiscount;
    }
}
