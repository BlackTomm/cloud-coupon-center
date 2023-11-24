package org.coding.coupon.template;

import org.coding.coupon.caculation.ShoppingComponent;

/**
 * Description: 根据不同的优惠模版计算优惠金额
 * Create by blacktom on 2023/11/24
 */
public interface RuleTemplate {
    ShoppingComponent calculate(ShoppingComponent shoppingComponent);
}
