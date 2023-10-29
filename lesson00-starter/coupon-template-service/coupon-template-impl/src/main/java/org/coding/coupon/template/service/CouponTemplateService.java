package org.coding.coupon.template.service;

import org.coding.coupon.template.domains.CouponTemplateInfo;
import org.coding.coupon.template.domains.PagedCouponTemplateInfo;
import org.coding.coupon.template.domains.TemplateSearchParams;

import java.util.Map;
import java.util.Set;

/**
 * Description:
 * Create by blacktom on 2023/10/30
 */
public interface CouponTemplateService {

    //创建模版
    CouponTemplateInfo createTemplate(CouponTemplateInfo templateInfo);

    CouponTemplateInfo cloneTemplate(long templateId);

    // 模板查询（分页）
    PagedCouponTemplateInfo search(TemplateSearchParams templateSearchParams);

    // 通过模板ID查询优惠券模板
    CouponTemplateInfo loadTemplateInfo(long id);

    // 让优惠券模板无效
    void deleteTemplate(long id);

    // 批量查询
    // Map是模板ID，key是模板详情
    Map<Long, CouponTemplateInfo> getTemplateInfoMap(Set<Long> ids);
}
