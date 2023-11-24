package org.coding.coupon.calculation.factory;

import com.google.common.collect.Maps;
import org.coding.coupon.caculation.ShoppingComponent;
import org.coding.coupon.template.RuleTemplate;
import org.coding.coupon.template.domains.CouponTemplateInfo;
import org.coding.coupon.template.enums.CouponType;
import org.coding.coupon.template.impl.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * Description:
 * Create by blacktom on 2023/11/24
 */
@Component
public class CouponTemplateFactory implements InitializingBean {

    private static final Map<CouponType, RuleTemplate> couponRuleTemplates = Maps.newHashMap();

    public RuleTemplate getTemplate(ShoppingComponent shoppingComponent) {
        //未传入优惠券，则无优惠
        if (CollectionUtils.isEmpty(shoppingComponent.getCouponInfos())) {
            //默认不优惠
            return couponRuleTemplates.get(CouponType.UNKNOWN);
        }

        CouponTemplateInfo couponTemplateInfo = shoppingComponent.getCouponInfos().get(0).getTemplateInfo();
        CouponType couponType = CouponType.convert(couponTemplateInfo.getType());
        return couponRuleTemplates.get(couponType);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //默认兜底，不作任何优惠
        couponRuleTemplates.put(CouponType.UNKNOWN, new DummyTemplate());
        //满减券
        couponRuleTemplates.put(CouponType.MONEY_OFF, new MoneyOffTemplate());
        //折扣券
        couponRuleTemplates.put(CouponType.DISCOUNT, new DiscountTemplate());
        //随机立减
        couponRuleTemplates.put(CouponType.RANDOM_DISCOUNT, new RandomReductionTemplate());
        //物业下单优惠翻倍
        couponRuleTemplates.put(CouponType.LONELY_NIGHT_MONEY_OFF, new LonelyNightTemplate());
        // 反 pua
        couponRuleTemplates.put(CouponType.ANTI_PUA, new AntiPuaTemplate());
    }
}
