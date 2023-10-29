package org.coding.coupon.template.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.coding.coupon.template.domains.rules.TemplateRule;

import javax.validation.constraints.NotNull;

/**
 * Description: 券批次信息，汇聚规则、创建信息
 * Create by blacktom on 2023/10/29
 */
@Data
@Builder
@AllArgsConstructor
public class CouponTemplateInfo {
    private long id;

    @NotNull
    private String name;

    @NotNull
    private String desc;


    private String type;

    private long shopId;

    private TemplateRule rule;

    private boolean available;
}
