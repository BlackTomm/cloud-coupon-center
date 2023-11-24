package org.coding.coupon.template;

import lombok.extern.slf4j.Slf4j;
import org.coding.coupon.caculation.Product;
import org.coding.coupon.caculation.ShoppingComponent;
import org.coding.coupon.template.domains.CouponTemplateInfo;
import org.coding.coupon.template.domains.rules.TemplateRule;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * Description:
 * Create by blacktom on 2023/11/24
 */
@Slf4j
public abstract class AbstractRuleTemplate implements RuleTemplate {
    @Override
    public ShoppingComponent calculate(ShoppingComponent shoppingComponent) {
        long orderTotalAmount = getOrderTotalAmount(shoppingComponent.getProducts());
        Map<Long, Long> shopIdOrderTotalAmountMap = getOrderTotalAmountGroupByShopId(shoppingComponent.getProducts());

        //先做单个优惠券计算
        CouponTemplateInfo couponTemplateInfo = shoppingComponent.getCouponInfos().get(0).getTemplateInfo();
        //当前券可用门店，如果为空则作用于全店券
        Long shopId = couponTemplateInfo.getShopId();
        //获取当前门店订单金额
        Long shopOrderAmount = shopId == null ? orderTotalAmount : shopIdOrderTotalAmountMap.get(shopId);

        if (shopOrderAmount == null || shopOrderAmount < couponTemplateInfo.getRule().getQuota()) {
            log.warn("AbstractRuleTemplate#calculate | Totals of amount not meet, ur coupons are not applicable to this order");
            shoppingComponent.setOrderTotalPrice(orderTotalAmount);
            shoppingComponent.setCouponInfos(Collections.emptyList());
            return shoppingComponent;
        }

        //子类计算优惠金额
        long couponOrderPrice = calculateCouponOrderPrice(orderTotalAmount, shopOrderAmount, couponTemplateInfo.getRule());
        if (couponOrderPrice < minOrderPrice()) {
            //避免 0 元单
            couponOrderPrice = minOrderPrice();
        }
        shoppingComponent.setOrderTotalPrice(couponOrderPrice);
        return shoppingComponent;
    }

    protected abstract long calculateCouponOrderPrice(long orderTotalAmount, long shopIdOrderTotalAmount, TemplateRule rule);

    /*每个订单最少必须支付1分钱*/
    private long minOrderPrice() {
        return 1L;
    }

    private Map<Long, Long> getOrderTotalAmountGroupByShopId(List<Product> products) {
        return products.stream().collect(Collectors.groupingBy(Product::getShopId,
                Collectors.summingLong(product -> product.getPrice() * product.getCount())));
    }

    /**
     * 获取订单中金额
     */
    private long getOrderTotalAmount(@NotEmpty List<Product> products) {
        return products.stream().mapToLong(product -> product.getPrice() * product.getCount()).sum();
    }

    /**
     * 四舍五入
     */
    protected long convertToDecimal(Double value) {
        return new BigDecimal(value).setScale(0, RoundingMode.HALF_UP).longValue();
    }
}
