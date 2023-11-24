package org.coding.coupon.caculation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.coding.coupon.template.domains.CouponInfo;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Description:
 * Create by blacktom on 2023/11/24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingComponent {
    /**
     * 用户 id
     */
    @NotNull
    private String userId;

    /**
     * 加车商品
     */
    @NotEmpty
    private List<Product> products;

    /**
     * 优惠券信息
     */
    private List<CouponInfo> couponInfos;

    private long orderTotalPrice;
}
