package org.coding.coupon.template.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Description:
 * Create by blacktom on 2023/10/29
 */
@Data
@Builder
@AllArgsConstructor
public class PagedCouponTemplateInfo {
    public List<CouponTemplateInfo> templates;

    private int page;

    private long total;
}
