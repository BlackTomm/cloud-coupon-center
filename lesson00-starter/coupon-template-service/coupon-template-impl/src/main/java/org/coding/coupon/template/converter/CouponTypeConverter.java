package org.coding.coupon.template.converter;

import org.coding.coupon.template.enums.CouponType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Description:
 * Create by blacktom on 2023/10/29
 */
@Converter
public class CouponTypeConverter implements AttributeConverter<CouponType, String> {


    @Override
    public String convertToDatabaseColumn(CouponType couponType) {
        return couponType.getCode();
    }

    @Override
    public CouponType convertToEntityAttribute(String code) {
        return CouponType.convert(code);
    }
}
