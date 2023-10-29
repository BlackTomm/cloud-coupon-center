package org.coding.coupon.template.service.impl;

import org.coding.coupon.template.domains.CouponTemplateInfo;
import org.coding.coupon.template.domains.PagedCouponTemplateInfo;
import org.coding.coupon.template.domains.TemplateSearchParams;
import org.coding.coupon.template.service.CouponTemplateService;

import java.util.Map;
import java.util.Set;

/**
 * Description:
 * Create by blacktom on 2023/10/30
 */
public class CouponTemplateServiceImpl  implements CouponTemplateService {
    @Override
    public CouponTemplateInfo createTemplate(CouponTemplateInfo templateInfo) {
        return null;
    }

    @Override
    public CouponTemplateInfo cloneTemplate(long templateId) {
        return null;
    }

    @Override
    public PagedCouponTemplateInfo search(TemplateSearchParams templateSearchParams) {
        return null;
    }

    @Override
    public CouponTemplateInfo loadTemplateInfo(long id) {
        return null;
    }

    @Override
    public void deleteTemplate(long id) {

    }

    @Override
    public Map<Long, CouponTemplateInfo> getTemplateInfoMap(Set<Long> ids) {
        return null;
    }
}
