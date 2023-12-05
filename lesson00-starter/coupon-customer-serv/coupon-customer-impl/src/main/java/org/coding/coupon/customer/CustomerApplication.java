package org.coding.coupon.customer;

import org.coding.coupon.customer.config.CanaryRuleConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Description:
 * Create by blacktom on 2023/11/29
 */
@SpringBootApplication(scanBasePackages = {"org.coding.coupon"})
@EnableJpaAuditing
@LoadBalancerClient(value = "coupon-template-serv",configuration = CanaryRuleConfiguration.class)
@EnableFeignClients(basePackages = {"org.coding.coupon"})
public class CustomerApplication {
    public static void main(String[] args) {
        SpringApplication.run(CustomerApplication.class, args);
    }
}
