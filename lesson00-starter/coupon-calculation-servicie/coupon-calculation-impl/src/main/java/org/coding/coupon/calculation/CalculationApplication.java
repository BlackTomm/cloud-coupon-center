package org.coding.coupon.calculation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Description:
 * Create by blacktom on 2023/11/25
 */
@SpringBootApplication(scanBasePackages = {"org.coding.coupon.calculation"})
public class CalculationApplication {
    public static void main(String[] args) {
        SpringApplication.run(CalculationApplication.class, args);
    }
}
