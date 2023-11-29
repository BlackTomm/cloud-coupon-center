package org.coding.coupon.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Description:
 * Create by blacktom on 2023/11/29
 */
@SpringBootApplication(scanBasePackages = {"org.coding.coupon.customer"})
@EnableJpaAuditing
public class CustomerApplication {
    public static void main(String[] args) {
        SpringApplication.run(CustomerApplication.class, args);
    }
}
