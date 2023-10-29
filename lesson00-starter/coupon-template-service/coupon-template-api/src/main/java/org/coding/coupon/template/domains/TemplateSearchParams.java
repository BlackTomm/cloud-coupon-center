package org.coding.coupon.template.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Description:
 * Create by blacktom on 2023/10/29
 */
@Data
@AllArgsConstructor
@Builder
public class TemplateSearchParams {
    private long id;

    private String name;

    private String type;

    private long shopId;

    private boolean available;

    private int page;

    private int pageSize;
}
