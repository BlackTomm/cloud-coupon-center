package org.coding.coupon.template.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description:
 * Create by blacktom on 2023/10/29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CouponInfo {
    private long id;

    private long templateId;

    private long userId;

    private long shopId;

    private int status;

    private CouponTemplateInfo templateInfo;
}
