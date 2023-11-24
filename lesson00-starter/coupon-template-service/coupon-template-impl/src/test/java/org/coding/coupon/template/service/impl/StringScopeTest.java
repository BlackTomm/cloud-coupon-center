package org.coding.coupon.template.service.impl;

import org.junit.jupiter.api.Test;

/**
 * Description:
 * Create by blacktom on 2023/11/20
 */
public class StringScopeTest {
    private static final String BYBT = "bt_";

    @Test
    public void  testScope() {
        String batchActivityKey = "bt_178883912,FP_shasdjakl";
        System.out.println("testScope before: " + System.identityHashCode(batchActivityKey));
        boolean flag = isValidBatch(batchActivityKey) || isValidActivity(batchActivityKey);
        System.out.println("testScope after: " + System.identityHashCode(batchActivityKey));
        System.out.println("batchActivityKey = " + batchActivityKey);
    }

    private boolean isValidBatch(String batchActivityKey) {
        System.out.println("isValidBatch before: " + System.identityHashCode(batchActivityKey));
        batchActivityKey = removePrefix(batchActivityKey);
        System.out.println("isValidBatch after: " + System.identityHashCode(batchActivityKey));
        return false;
    }

    private boolean isValidActivity(String batchActivityKey) {
        System.out.println("isValidActivity before: " + System.identityHashCode(batchActivityKey));
        batchActivityKey = removePrefix(batchActivityKey);
        System.out.println("isValidActivity after: " + System.identityHashCode(batchActivityKey));
        return true;
    }

    private String removePrefix(String modifiedKey) {
//        String modifiedKey = batchActivityKey;
        if (modifiedKey.startsWith(BYBT)) {
            modifiedKey = modifiedKey.substring(BYBT.length());
        }
        return modifiedKey;
    }
}
