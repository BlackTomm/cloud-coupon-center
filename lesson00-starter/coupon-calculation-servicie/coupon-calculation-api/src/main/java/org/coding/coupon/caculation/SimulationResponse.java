package org.coding.coupon.caculation;

import com.google.common.collect.Maps;
import lombok.Data;

import java.util.Map;

/**
 * Description: 优惠价格试算
 * Create by blacktom on 2023/11/24
 */
@Data
public class SimulationResponse {
    /**
     * 最优券
     */
    private long bestCouponId;

    private Map<Long, Long> couponToOrderPrice = Maps.newHashMap();
}
