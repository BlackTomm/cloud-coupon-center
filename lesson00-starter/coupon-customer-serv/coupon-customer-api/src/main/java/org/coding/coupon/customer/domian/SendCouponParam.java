package org.coding.coupon.customer.domian;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * Description:
 * Create by blacktom on 2023/11/25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendCouponParam {

    @NotNull
    private String userId;

    /**
     * 模版 id
     */
    @NotNull
    private long templateId;
}
