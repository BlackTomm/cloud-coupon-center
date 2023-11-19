package org.coding.coupon.template.domains.rules;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Description:
 * Create by blacktom on 2023/10/29
 */
@Data
@AllArgsConstructor
public class Discount {
    /**
     * 券门槛
     */
    private long quota;

    /**
     * 折扣优惠
     */
    private long discount;


}
