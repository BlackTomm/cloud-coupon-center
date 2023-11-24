package org.coding.coupon.calculation.service.impl;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.coding.coupon.caculation.ShoppingComponent;
import org.coding.coupon.caculation.SimulationResponse;
import org.coding.coupon.calculation.factory.CouponTemplateFactory;
import org.coding.coupon.calculation.service.CouponCalculationService;
import org.coding.coupon.template.RuleTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description:
 * Create by blacktom on 2023/11/24
 */
@Slf4j
@Service
public class CouponCalculationServiceImpl implements CouponCalculationService {
    @Autowired
    private CouponTemplateFactory couponProcessorFactory;
    /**
     * 计算第一张券的优惠金额
     */
    @Override
    public ShoppingComponent calculateOrderPrice(ShoppingComponent shoppingComponent) {
        log.info("in | calculateOrderPrice : {}", JSON.toJSON(shoppingComponent));
        RuleTemplate ruleTemplate = couponProcessorFactory.getTemplate(shoppingComponent);
        return ruleTemplate.calculate(shoppingComponent);
    }

    @Override
    public SimulationResponse findBestCoupons(ShoppingComponent shoppingComponent) {
        return null;
    }
}
