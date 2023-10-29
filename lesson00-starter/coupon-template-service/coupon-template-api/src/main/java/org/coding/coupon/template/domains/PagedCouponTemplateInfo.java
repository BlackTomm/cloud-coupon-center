package org.coding.coupon.template.domains;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Description:
 * Create by blacktom on 2023/10/29
 */
@Data
@AllArgsConstructor
public class PagedCouponTemplateInfo {
    public List<CouponTemplateInfo> templates;

    private int page;

    private long total;
}
