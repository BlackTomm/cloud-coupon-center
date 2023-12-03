package org.coding.coupon.customer.config;

import org.coding.coupon.customer.loadbalance.CanaryRule;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

/**
 * Description: 负载均衡策略
 * Create by blacktom on 2023/12/03
 */
public class CanaryRuleConfiguration {
    @Bean
    public ReactorLoadBalancer<ServiceInstance> instanceReactorLoadBalancer(Environment environment,
                                                                            LoadBalancerClientFactory loadBalancerClientFactory) {
       String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
       return new CanaryRule(loadBalancerClientFactory.getLazyProvider(name,
               ServiceInstanceListSupplier.class), name);
    }
}
