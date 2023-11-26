package org.coding.coupon.customer.dao;

import org.coding.coupon.customer.entity.Coupon;
import org.coding.coupon.customer.enums.CouponStatus;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

/**
 * Description:
 * Create by blacktom on 2023/11/26
 */
public interface CouponCustomerDao extends JpaRepository<Coupon, Long> {
    long countByUserIdAndTemplateId(String userId, long templateId);

    List<Coupon> findAllCoupons(Example searchParams, Pageable pageable);

    @Query(value = "select c from Coupon c where c.userId = :userId and c.couponId IN :couponIds and c.couponStatus = :couponStatus")
    List<Coupon> findCouponsByIds(@Param("userId") String userId, Collection<String> couponIds, CouponStatus couponStatus);
}
