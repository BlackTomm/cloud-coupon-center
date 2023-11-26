package org.coding.coupon.customer.service;

import org.coding.coupon.caculation.ShoppingComponent;
import org.coding.coupon.caculation.SimulationResponse;
import org.coding.coupon.customer.domian.BaseResponse;
import org.coding.coupon.customer.domian.SearchCoupponParam;
import org.coding.coupon.customer.domian.SendCouponParam;
import org.coding.coupon.customer.entity.Coupon;
import org.coding.coupon.template.domains.CouponInfo;

import java.util.List;

/**
 * Description:
 * Create by blacktom on 2023/11/25
 */
public interface CouponCustomerService {
    //领券
    Coupon sendCoupons(SendCouponParam sendCouponRequest);

    //作废券
    BaseResponse invalidCoupon(String userId, long couponId);

    //查询用户券
    List<CouponInfo> findCoupons(SearchCoupponParam searchCoupponParam);

    /**
     * 用券
     */
    ShoppingComponent useCoupon(ShoppingComponent shoppingCartComponent);

    /**
     * 推荐券
     */
    SimulationResponse findBestCoupons(ShoppingComponent shoppingComponent);
}
