package org.coding.coupon.template.converter;

import org.coding.coupon.template.domains.CouponTemplateInfo;
import org.coding.coupon.template.dao.CouponTemplate;

/**
 * Description: 随着 client 包入参和数据库字段存在差异时，此处可以兼容
 * Create by blacktom on 2023/11/19
 */
public class CouponTemplateConverter {
    public static CouponTemplateInfo convertToCouponTemplateInfo(CouponTemplate couponTemplate) {
        return CouponTemplateInfo.builder()
                .id(couponTemplate.getId())
                .name(couponTemplate.getName())
                .desc(couponTemplate.getDescription())
                .available(couponTemplate.isAvailable())
                .type(couponTemplate.getCouponType().getCode())
                .shopId(couponTemplate.getShopId())
                .rule(couponTemplate.getRule())
                .build();
    }
}
