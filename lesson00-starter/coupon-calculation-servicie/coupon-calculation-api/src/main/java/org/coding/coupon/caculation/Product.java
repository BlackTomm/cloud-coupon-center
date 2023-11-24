package org.coding.coupon.caculation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description:
 * Create by blacktom on 2023/11/24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    private String skuId;

    /**
     * 单品价格
     */
    private long price;

    /**
     * 商品数量
     */
    private int count;

    /**
     * 门店
     */
    private long shopId;
}
