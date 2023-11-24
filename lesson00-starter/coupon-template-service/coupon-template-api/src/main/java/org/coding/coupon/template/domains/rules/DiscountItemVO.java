package org.coding.coupon.template.domains.rules;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Description:
 * Create by blacktom on 2023/10/29
 */
@Data
@AllArgsConstructor
public class DiscountItemVO {
    /**
     * 当前折扣门槛
     */
    private long quota;


    /**
     * 折扣优惠，70 即 0.7 折
     */
    private long discount;


}
