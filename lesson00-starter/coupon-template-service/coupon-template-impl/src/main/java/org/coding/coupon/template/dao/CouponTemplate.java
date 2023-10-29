package org.coding.coupon.template.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.coding.coupon.template.converter.CouponTypeConverter;
import org.coding.coupon.template.domains.rules.TemplateRule;
import org.coding.coupon.template.enums.CouponType;
import org.coding.coupon.template.converter.RuleConverter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * Description:
 * Create by blacktom on 2023/10/29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "coupon_template")
public class CouponTemplate implements Serializable {

    @Id//主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)//自增
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "available", nullable = false)
    private boolean available;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "shop_id")
    private long shopId;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "coupon_type", nullable = false)
    @Convert(converter = CouponTypeConverter.class)
    private CouponType couponType;

    // 创建时间，通过@CreateDate注解自动填值（需要配合@JpaAuditing注解在启动类上生效）
    @CreatedDate
    @Column(name = "create_time", nullable = false)
    private Date createTime;

    // 优惠券核算规则，平铺成JSON字段
    @Column(name = "rule", nullable = false)
    @Convert(converter = RuleConverter.class)
    private TemplateRule rule;
}
