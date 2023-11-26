package org.coding.coupon.customer.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Description:
 * Create by blacktom on 2023/11/26
 */
@AllArgsConstructor
@Getter
public enum SystemErrorCode {
    COUPON_DATA_NOT_FOUND(-1, "could not find available coupon");

    /**
     * 错误码
     */
    private int code;

    /**
     * 文案
     */
    private String desc;
}
