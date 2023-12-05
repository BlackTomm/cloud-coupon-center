package org.coding.coupon.caculation.api;

import org.coding.coupon.caculation.ShoppingComponent;
import org.coding.coupon.caculation.SimulationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Description:
 * Create by blacktom on 2023/12/05
 */
@FeignClient(value = "coupon-calculation-serv", path = "/calculation")
public interface CouponCalculationService {

    @PostMapping("/calculateOrderPrice")
    ShoppingComponent calculateOrderPrice(ShoppingComponent shoppingComponent);


    /**
     * 优惠券列表挨个试算
     * 给客户提示每个可用券的优惠额度，帮助挑选
     */
    @PostMapping("/findBestCoupons")
    SimulationResponse findBestCoupons(@RequestBody ShoppingComponent shoppingComponent);
}
