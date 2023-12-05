package org.coding.coupon.template.feign.fallback;

import lombok.extern.slf4j.Slf4j;
import org.coding.coupon.template.api.CouponTemplateService;
import org.coding.coupon.template.domains.CouponTemplateInfo;
import org.coding.coupon.template.domains.PagedCouponTemplateInfo;
import org.coding.coupon.template.domains.TemplateSearchParams;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 * Create by blacktom on 2023/12/05
 */
@Slf4j
@Component
public class CouponTemplateServiceFallbackFactory implements FallbackFactory<CouponTemplateService> {
    @Override
    public CouponTemplateService create(Throwable cause) {
        return new CouponTemplateService() {
            @Override
            public CouponTemplateInfo createTemplate(CouponTemplateInfo templateInfo) {
                log.info("fallback createTemplate");
                return null;
            }

            @Override
            public CouponTemplateInfo cloneTemplate(long templateId) {
                log.info("fallback cloneTemplate");
                return null;
            }

            @Override
            public CouponTemplateInfo loadTemplateInfo(long id) {
                log.info("fallback loadTemplateInfo");
                return null;
            }

            @Override
            public Map<Long, CouponTemplateInfo> batchLoadTemplate(Collection<Long> ids) {
                log.info("fallback batchLoadTemplate");
                return new HashMap<>();
            }

            @Override
            public PagedCouponTemplateInfo search(TemplateSearchParams request) {
                log.info("fallback searchTemplate");

                return null;
            }

            @Override
            public void deleteTemplate(Long id) {
                log.info("fallback deleteTemplate");
            }
        };
    }
}
