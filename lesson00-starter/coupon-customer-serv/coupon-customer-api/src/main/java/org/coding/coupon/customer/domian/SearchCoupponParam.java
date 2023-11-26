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
@AllArgsConstructor
@NoArgsConstructor
public class SearchCoupponParam {
    @NotNull
    private String userId;

    private long shopId;

    private int couponStatus;

    private int page;

    private int pageSize;
}
