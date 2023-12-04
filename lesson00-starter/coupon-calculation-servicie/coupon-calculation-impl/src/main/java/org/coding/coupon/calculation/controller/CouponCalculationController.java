package org.coding.coupon.calculation.controller;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.coding.coupon.caculation.ShoppingComponent;
import org.coding.coupon.caculation.SimulationResponse;
import org.coding.coupon.calculation.service.CalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Description:
 * Create by blacktom on 2023/11/25
 */
@Slf4j
@RestController
@RequestMapping("/calculation")
public class CouponCalculationController {

    @Autowired
    private CalculationService calculationService;

    /**
     * 计算优惠金额
     */
    @PostMapping("/calculateOrderPrice")
    @ResponseBody
    public ShoppingComponent calculateOrderPrice(@RequestBody ShoppingComponent shoppingComponent) {
        log.info("do calculateOrderPrice: {}", JSON.toJSONString(shoppingComponent));
        return calculationService.calculateOrderPrice(shoppingComponent);
    }


    /**
     * 优惠券列表挨个试算
     * 给客户提示每个可用券的优惠额度，帮助挑选
     */
    @PostMapping("/findBestCoupons")
    @ResponseBody
    SimulationResponse findBestCoupons(@RequestBody ShoppingComponent shoppingComponent) {
        log.info("do findBestCoupons: {}", JSON.toJSONString(shoppingComponent));
        return calculationService.findBestCoupons(shoppingComponent);
    }
}
