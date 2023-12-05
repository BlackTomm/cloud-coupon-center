package org.coding.coupon.calculation.service.impl;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.coding.coupon.caculation.ShoppingComponent;
import org.coding.coupon.caculation.SimulationResponse;
import org.coding.coupon.calculation.factory.CouponTemplateFactory;
import org.coding.coupon.calculation.service.CalculationService;
import org.coding.coupon.template.RuleTemplate;
import org.coding.coupon.template.domains.CouponInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * Description:
 * Create by blacktom on 2023/11/24
 */
@Slf4j
@Service
public class CalculationServiceImpl implements CalculationService {
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

    /**
     * 计算 所有券的最优金额
     */
    @Override
    public SimulationResponse findBestCoupons(ShoppingComponent shoppingComponent) {
        log.info("in | findBestCoupons : {}", JSON.toJSON(shoppingComponent));

        SimulationResponse simulationResponse = new SimulationResponse();
        long minOrderPrice = Long.MAX_VALUE;

        for (CouponInfo couponInfo : shoppingComponent.getCouponInfos()) {
            ShoppingComponent couponShoppingComponent = new ShoppingComponent();
            couponShoppingComponent.setProducts(shoppingComponent.getProducts());
            couponShoppingComponent.setCouponInfos(Collections.singletonList(couponInfo));
            couponShoppingComponent = couponProcessorFactory.getTemplate(couponShoppingComponent).calculate(couponShoppingComponent);

            simulationResponse.getCouponToOrderPrice().put(couponInfo.getCouponId(), couponShoppingComponent.getOrderTotalPrice());

            //计算最优惠的券
            if (minOrderPrice > couponShoppingComponent.getOrderTotalPrice()) {
                minOrderPrice = couponShoppingComponent.getOrderTotalPrice();
                simulationResponse.setBestCouponId(couponInfo.getCouponId());
            }
        }

        return simulationResponse;
    }
}
