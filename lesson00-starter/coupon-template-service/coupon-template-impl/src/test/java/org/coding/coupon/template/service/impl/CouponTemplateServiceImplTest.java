package org.coding.coupon.template.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.coding.coupon.template.converter.CouponTemplateConverter;
import org.coding.coupon.template.dao.CouponTemplate;
import org.coding.coupon.template.domains.CouponTemplateInfo;
import org.coding.coupon.template.domains.rules.TemplateRule;
import org.coding.coupon.template.enums.CouponType;
import org.coding.coupon.template.api.CouponTemplateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.Date;

@SpringBootTest
public class CouponTemplateServiceImplTest {
    @Autowired
    private CouponTemplateService couponTemplateService;

    @Test
    public void createTemplateTest() throws JsonProcessingException {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 30);
        Date endTime = calendar.getTime();

        TemplateRule templateRule = TemplateRule.builder()
                .highDiscount(10)
                .limitation(3)
                .quota(5)
                .discount(2)
                .endTime(endTime)
                .startTime(new Date())
                .build();

        CouponTemplate couponTemplate = CouponTemplate.builder()
                .couponType(CouponType.DISCOUNT)
                .shopId(1001L)
                .available(true)
                .name("coding-test")
                .description("测试批次")
                .createTime(new Date())
                .rule(templateRule).build();
        CouponTemplateInfo couponTemplateInfo = couponTemplateService.createTemplate(CouponTemplateConverter.convertToCouponTemplateInfo(couponTemplate));

        System.out.println("insert couponTemplate: " + new ObjectMapper().writeValueAsString(couponTemplateInfo));

        System.out.println("select couponTemplate:" + new ObjectMapper().writeValueAsString(couponTemplateService.loadTemplateInfo(couponTemplateInfo.getId())));
    }
}