package org.coding.coupon.customer.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.coding.coupon.customer.converter.CouponStatusConverter;
import org.coding.coupon.customer.enums.CouponStatus;
import org.coding.coupon.template.domains.CouponTemplateInfo;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.*;
import java.util.Date;

/**
 * Description:
 * Create by blacktom on 2023/11/26
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EnableJpaAuditing
@Entity
@Table(name = "coupon")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "coupon_id", nullable = false)
    private String couponId;

    @Column(name = "template_id", nullable = false)
    private long templateId;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "shop_id")
    private long shopId;

    @Column (name = "coupon_status", nullable = false)
    @Convert(converter = CouponStatusConverter.class)
    private CouponStatus couponStatus;

    // 被Transient标记的属性是不会被持久化的
    @Transient
    private CouponTemplateInfo templateInfo;

    // 获取时间自动生成
    @CreatedDate
    @Column(name = "created_time", nullable = false)
    private Date createdTime;
}
