package org.coding.coupon.template.api;

import org.coding.coupon.template.domains.CouponTemplateInfo;
import org.coding.coupon.template.domains.PagedCouponTemplateInfo;
import org.coding.coupon.template.domains.TemplateSearchParams;
import org.coding.coupon.template.feign.fallback.CouponTemplateServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.Map;

/**
 * Description: 对外的 client 接口
 * Create by blacktom on 2023/12/04
 */
@FeignClient(value = "coupon-template-serv", path = "/template",
//        fallback = CouponTemplateServiceFallback.class
        fallbackFactory = CouponTemplateServiceFallbackFactory.class
)
public interface CouponTemplateService {

    //创建模版
    @PostMapping("createTemplate")
    CouponTemplateInfo createTemplate(CouponTemplateInfo templateInfo);

    @GetMapping("cloneTemplate")
    CouponTemplateInfo cloneTemplate(@RequestParam("id") long templateId);

    // 通过模板ID查询优惠券模板
    @GetMapping("/loadTemplate")
    CouponTemplateInfo loadTemplateInfo(@RequestParam("id") long id);

    @GetMapping("/batchLoadTemplate")
    Map<Long, CouponTemplateInfo> batchLoadTemplate(@RequestParam("ids") Collection<Long> ids);

    // 搜索模板
    @PostMapping("/search")
    PagedCouponTemplateInfo search(TemplateSearchParams request);

    // 优惠券无效化
    @DeleteMapping("/deleteTemplate")
    void deleteTemplate(@RequestParam("id") Long id);
}

