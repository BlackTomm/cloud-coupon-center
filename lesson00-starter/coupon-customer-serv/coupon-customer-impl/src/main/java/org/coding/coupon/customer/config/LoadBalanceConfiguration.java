package org.coding.coupon.customer.config;

import feign.Logger;
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

    /**
     * OpenFeign总共有四种不同的日志级别，我来带你了解一下这四种级别下OpenFeign向日志中写入的内容。
     * 1、NONE:不记录任何信息，这是OpenFeign默认的日志级别；
     * 2、 BASIC:只记录服务请求的URL、HTTP Method、响应状态码（如200、404等）和服务调用的执行时间；
     * 3、HEADERS:在BASIC的基础上，还记录了请求和响应中的HTTP Headers;
     * 4、FULL:在HEADERS级别的基础上，还记录了服务请求和服务响应中的Body和 metadata,FULL级别记录了最完成的调用信息。
     */
    @Bean
    Logger.Level feignLogger() {
        return Logger.Level.FULL;
    }
}
