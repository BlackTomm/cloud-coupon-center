package org.coding.coupon.customer.enums;

import lombok.Getter;

/**
 * Description:
 * Create by blacktom on 2023/12/03
 */
@Getter
public enum BalanceConstant {
    TRAFFIC_VERSION("traffic-version","压测流量标识-金丝雀");
    private String flag;

    private String desc;
    BalanceConstant(String flag, String desc) {
        this.flag = flag;
        this.desc = desc;
    }
}
