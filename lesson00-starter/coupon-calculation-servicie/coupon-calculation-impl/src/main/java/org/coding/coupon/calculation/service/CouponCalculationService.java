package org.coding.coupon.calculation.service;

import org.coding.coupon.caculation.ShoppingComponent;
import org.coding.coupon.caculation.SimulationResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Description:
 * Create by blacktom on 2023/11/24
 */
public interface CouponCalculationService {
    /**
     * 计算优惠金额
     */
    ShoppingComponent calculateOrderPrice(@RequestBody ShoppingComponent shoppingComponent);


    SimulationResponse findBestCoupons(@RequestBody ShoppingComponent shoppingComponent);
}
