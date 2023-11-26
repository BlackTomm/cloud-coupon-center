package org.coding.coupon.customer.domian;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description:
 * Create by blacktom on 2023/11/25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse {
    private boolean success;

    private int descCode;

    private String desc;
}
