package org.coding.coupon.template.controller;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.coding.coupon.template.domains.CouponTemplateInfo;
import org.coding.coupon.template.domains.PagedCouponTemplateInfo;
import org.coding.coupon.template.domains.TemplateSearchParams;
import org.coding.coupon.template.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Map;


/**
 * Description:
 * Create by blacktom on 2023/11/19
 */
@Slf4j
@RequestMapping("/template")
@RestController
public class CouponTemplateController {
    @Autowired
    private TemplateService couponTemplateService;

    @PostMapping("createTemplate")
    public CouponTemplateInfo createTemplate(@Valid @RequestBody CouponTemplateInfo couponTemplateInfo) {
        log.info("Create coupon template: data={}", couponTemplateInfo);
        return couponTemplateService.createTemplate(couponTemplateInfo);
    }


    @GetMapping("cloneTemplate")
    public CouponTemplateInfo cloneTemplate(@Valid @RequestParam("id") Long templateId) {
        log.info("Clone coupon template: id={}", templateId);
        return couponTemplateService.cloneTemplate(templateId);
    }


    @GetMapping("/loadTemplate")
    public CouponTemplateInfo loadTemplate(@Valid @RequestParam("id") Long templateId) {
        log.info("Load coupon template: id={}", templateId);
        //openFeign超时时间验证
//        try {
//            Thread.sleep(300 );
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        return couponTemplateService.loadTemplateInfo(templateId);
    }

    @GetMapping("/batchLoadTemplate")
    public Map<Long, CouponTemplateInfo> batchLoadTemplate(@RequestParam("ids") Collection<Long> ids) {
        log.info("getTemplateInBatch: {}", JSON.toJSONString(ids));
        return couponTemplateService.getTemplateInfoMap(ids);
    }

    // 搜索模板
    @PostMapping("/search")
    public PagedCouponTemplateInfo search(@Valid @RequestBody TemplateSearchParams request) {
        log.info("search templates, payload={}", request);
        return couponTemplateService.search(request);
    }

    // 优惠券无效化
    @DeleteMapping("/deleteTemplate")
    public void deleteTemplate(@RequestParam("id") Long id){
        log.info("Load template, id={}", id);
        couponTemplateService.deleteTemplate(id);
    }

}
