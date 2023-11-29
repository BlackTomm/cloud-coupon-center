package org.coding.coupon.customer.service;

import com.alibaba.fastjson.JSON;
import org.coding.coupon.customer.dao.CouponCustomerDao;
import org.coding.coupon.customer.domian.SendCouponParam;
import org.coding.coupon.customer.entity.Coupon;
import org.coding.coupon.customer.enums.CouponStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

/**
 * Description:
 * Create by blacktom on 2023/11/30
 */
@SpringBootTest
public class CouponCustomerServiceImplTest {
    @Autowired
    private CouponCustomerService couponCustomerService;

    @Autowired
    private CouponCustomerDao couponCustomerDao;

    @Test
    public void sendCouponTest() {
        SendCouponParam sendCouponParam = new SendCouponParam();
        sendCouponParam.setTemplateId(3);
        sendCouponParam.setUserId("test1");

        Coupon coupon = couponCustomerService.sendCoupons(sendCouponParam);
        System.out.println("coupon = " + JSON.toJSON(coupon));
    }


    @Test
    public void saveCouponTest() {
        SendCouponParam sendCouponParam = new SendCouponParam();
        sendCouponParam.setTemplateId(3);
        sendCouponParam.setUserId("test1");

        Coupon coupon = Coupon.builder().userId(sendCouponParam.getUserId())
                .templateId(sendCouponParam.getTemplateId())
                .shopId(100)
                .createdTime(new Date())
                .couponStatus(CouponStatus.AVAILIABLE)
                .build();
        coupon = couponCustomerDao.save(coupon);
    }
}
