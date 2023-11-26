package org.coding.coupon.customer.service.impl;

import org.coding.coupon.customer.entity.Coupon;
import org.coding.coupon.template.domains.CouponInfo;

/**
 * Description:
 * Create by blacktom on 2023/11/26
 */
public class CouponConverter {
    public static CouponInfo convertToCoupon(Coupon coupon) {
        return CouponInfo.builder()
                .couponId(coupon.getCouponId())
                .status(coupon.getCouponStatus().getCode())
                .templateId(coupon.getTemplateId())
                .shopId(coupon.getShopId())
                .userId(coupon.getUserId())
                .templateInfo(coupon.getTemplateInfo())
                .build();
    }
}
