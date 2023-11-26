package org.coding.coupon.customer.enums;

import lombok.Getter;

import java.util.stream.Stream;

/**
 * Description: 券状态
 * Create by blacktom on 2023/11/25
 */
@Getter
public enum CouponStatus {
    AVAILIABLE("待使用", 1),
    USED("已使用", 2),
    INVALID("已作废", 3);
    private String desc;
    private int code;

    CouponStatus(String desc, int code) {
        this.desc = desc;
        this.code = code;
    }

    public static CouponStatus convert(Integer code) {
        if (code == null) {
            return null;
        }

        return Stream.of(values())
                .filter(status -> status.code == code)
                .findAny().orElse(null);
    }
}
