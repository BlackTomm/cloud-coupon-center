package org.coding.coupon.customer.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Description: 类中定义的Bean方法会被AnnotationConfigApplicationContext和AnnotationConfigWebApplicationContext扫描并初始化
 * Create by blacktom on 2023/11/29
 */
@Configuration
public class LoadBalanceConfiguration {

    @Bean
    @LoadBalanced
    public WebClient.Builder register() {
        return WebClient.builder();
    }
}
