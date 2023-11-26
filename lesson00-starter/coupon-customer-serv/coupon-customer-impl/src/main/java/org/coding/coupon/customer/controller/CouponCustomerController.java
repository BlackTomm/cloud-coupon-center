package org.coding.coupon.customer.controller;

import lombok.extern.slf4j.Slf4j;
import org.coding.coupon.caculation.ShoppingComponent;
import org.coding.coupon.caculation.SimulationResponse;
import org.coding.coupon.customer.domian.BaseResponse;
import org.coding.coupon.customer.domian.SearchCoupponParam;
import org.coding.coupon.customer.domian.SendCouponParam;
import org.coding.coupon.customer.entity.Coupon;
import org.coding.coupon.customer.service.CouponCustomerService;
import org.coding.coupon.template.domains.CouponInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Description:
 * Create by blacktom on 2023/11/26
 */
@Slf4j
@RestController
@RequestMapping("coupon-customer")
public class CouponCustomerController {
    @Autowired
    private CouponCustomerService couponCustomerService;

    @PostMapping("sendCoupons")
    public Coupon sendCoupons(@Valid @RequestBody SendCouponParam sendCouponRequest) {
        return couponCustomerService.sendCoupons(sendCouponRequest);
    }

    //作废券
    @PostMapping("invalidCoupon")
    public BaseResponse invalidCoupon(@RequestParam("userId") String userId, @RequestParam("couponId") String couponId) {
        return couponCustomerService.invalidCoupon(userId, couponId);
    }

    //查询用户券
    @PostMapping("findCoupons")
    public List<CouponInfo> findCoupons(@Valid @RequestBody SearchCoupponParam searchCoupponParam) {
        return couponCustomerService.findCoupons(searchCoupponParam);
    }

    /**
     * 用券
     */
    @PostMapping("useCoupon")
    public ShoppingComponent useCoupon(@Valid @RequestBody ShoppingComponent shoppingCartComponent) {
        return couponCustomerService.useCoupon(shoppingCartComponent);
    }

    /**
     * 推荐券
     */
    @PostMapping("findBestCoupons")
    public SimulationResponse findBestCoupons(@Valid @RequestBody ShoppingComponent shoppingComponent) {
        return couponCustomerService.findBestCoupons(shoppingComponent);
    }


}
