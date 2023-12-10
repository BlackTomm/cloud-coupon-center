package org.coding.coupon.gateway;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

/**
 * Description:
 * Create by blacktom on 2023/12/10
 */
@Configuration
public class RedisLimitConfig {
    public KeyResolver remoteHostLimitKey() {
        return exchange -> Mono.just(exchange.getRequest()
                .getRemoteAddress()
                .getAddress()
                .getHostAddress());
    }

    @Bean("templateReateLimiter")
    public RedisRateLimiter templateRateLimiter() {
        return new RedisRateLimiter(10,20);
    }
}
