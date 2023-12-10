package org.coding.coupon.template.service.impl;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.coding.coupon.template.converter.CouponTemplateConverter;
import org.coding.coupon.template.dao.CouponTemplate;
import org.coding.coupon.template.domains.CouponTemplateInfo;
import org.coding.coupon.template.domains.rules.TemplateRule;
import org.coding.coupon.template.enums.CouponType;
import org.coding.coupon.template.service.TemplateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.Date;

@SpringBootTest
public class CouponTemplateServiceImplTest {
    @Autowired
    private TemplateService couponTemplateService;

    @Test
    public void createTemplateTest() throws JsonProcessingException {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 30);
        Date endTime = calendar.getTime();

        TemplateRule templateRule = TemplateRule.builder()
                .highDiscount(20)
                .limitation(30)
                .quota(5)
                .discount(2)
                .endTime(endTime)
                .startTime(new Date())
                .build();

        CouponTemplate couponTemplate = CouponTemplate.builder()
                .couponType(CouponType.DISCOUNT)
                .shopId(1001L)
                .available(true)
                .name("coding-test2")
                .description("测试批次")
                .createTime(new Date())
                .rule(templateRule).build();
        CouponTemplateInfo couponTemplateInfo = couponTemplateService.createTemplate(CouponTemplateConverter.convertToCouponTemplateInfo(couponTemplate));

        System.out.println("insert couponTemplate: " + JSON.toJSON(couponTemplateInfo));

        System.out.println("select couponTemplate:" + JSON.toJSON(couponTemplateService.loadTemplateInfo(couponTemplateInfo.getId())));
    }
}