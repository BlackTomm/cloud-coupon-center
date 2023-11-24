package org.coding.coupon.template.impl;

import org.coding.coupon.template.AbstractRuleTemplate;
import org.coding.coupon.template.domains.rules.DiscountItemVO;
import org.coding.coupon.template.domains.rules.TemplateRule;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Description: 折扣券
 * Create by blacktom on 2023/11/24
 */
public class DiscountTemplate extends AbstractRuleTemplate {

    @Override
    protected long calculateCouponOrderPrice(long orderTotalAmount, long shopIdOrderTotalAmount, TemplateRule rule) {
        long discount = CollectionUtils.isEmpty(rule.getDiscountInfos()) ?
                rule.getDiscount() : getMaxDiscount(shopIdOrderTotalAmount, rule.getDiscountInfos());
        return getCoupnOrderPrice(orderTotalAmount,shopIdOrderTotalAmount, discount, rule.getHighDiscount());
    }

    /**
     * 根据店铺商品总金额*（1-折扣）得到优惠金额，不允许超过上限 highDiscount
     * @return 优惠后的订单总金额
     */
    private long getCoupnOrderPrice(long orderTotalAmount, long shopIdOrderTotalAmount, long discount, long highDiscount) {
        long discountPrice = convertToDecimal((double) (shopIdOrderTotalAmount * (1 - discount / 100)));
        return orderTotalAmount - Math.min(discountPrice, highDiscount);
    }

    private long getMaxDiscount(Long shopIdOrderTotalAmount, List<DiscountItemVO> discountInfos) {
        long currentDiscount = 0;
        //返回最大折扣
        for (DiscountItemVO discountInfo : discountInfos) {
            if (shopIdOrderTotalAmount < discountInfo.getQuota()) {
                break;
            }
            currentDiscount = discountInfo.getDiscount();
        }
        return currentDiscount;
    }

}
