package org.coding.coupon.customer.converter;

import org.coding.coupon.customer.enums.CouponStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Convert;

/**
 * Description:
 * Create by blacktom on 2023/11/26
 */
@Convert
public class CouponStatusConverter implements AttributeConverter<CouponStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(CouponStatus couponStatus) {
        return couponStatus.getCode();
    }

    @Override
    public CouponStatus convertToEntityAttribute(Integer couponStatus) {
        return CouponStatus.convert(couponStatus);
    }
}
