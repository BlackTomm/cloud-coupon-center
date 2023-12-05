package org.coding.coupon.customer.loadbalance;

import lombok.extern.slf4j.Slf4j;
import org.coding.coupon.customer.enums.BalanceConstant;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.*;
import org.springframework.cloud.loadbalancer.core.NoopServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.SelectedInstanceCallback;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.http.HttpHeaders;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Description: 可以将这个负载均衡策略单独拎出来，作为一个公共组件提供服务
 * Create by blacktom on 2023/12/03
 */
@Slf4j
public class CanaryRule implements ReactorServiceInstanceLoadBalancer {
    private String serviceId;

    private ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider;

    // 定义一个轮询策略的种子
    final AtomicInteger position = new AtomicInteger();

    public CanaryRule(ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider, String serviceId) {
        this.serviceId = serviceId;
        this.serviceInstanceListSupplierProvider = serviceInstanceListSupplierProvider;
    }

    @Override
    public Mono<Response<ServiceInstance>> choose(Request request) {
        ServiceInstanceListSupplier supplier = serviceInstanceListSupplierProvider
                .getIfAvailable(NoopServiceInstanceListSupplier::new);
        return supplier.get(request).next()
                .map(serviceInstances -> processInstanceResponse(supplier, serviceInstances, request));
    }

    private Response<ServiceInstance> processInstanceResponse(ServiceInstanceListSupplier supplier, List<ServiceInstance> serviceInstances, Request request) {
        Response<ServiceInstance> serviceInstanceResponse = getInstanceResponse(serviceInstances, request);
        if (supplier instanceof SelectedInstanceCallback && serviceInstanceResponse.hasServer()) {
            ((SelectedInstanceCallback) supplier).selectedServiceInstance(serviceInstanceResponse.getServer());
        }
        return serviceInstanceResponse;
    }

    private Response<ServiceInstance> getInstanceResponse(List<ServiceInstance> serviceInstances, Request request) {
        // 注册中心无可用实例，抛异常
        if (CollectionUtils.isEmpty(serviceInstances)) {
            log.warn("No instance available {}", serviceId);
            return new EmptyResponse();
        }

        // 从请求Header中获取特定的流量打标值
        // 注意：以下代码仅适用于WebClient调用，如果使用RestTemplate或者Feign则需要额外适配
        DefaultRequestContext context = (DefaultRequestContext) request.getContext();
        RequestData requestData = (RequestData) context.getClientRequest();
        HttpHeaders headers = requestData.getHeaders();

        //从 header中获取压测流量标识
        String trafficVersion = headers.getFirst(BalanceConstant.TRAFFIC_VERSION.getFlag());

        //没有找到压测流量标记，使用 RoundRobin查询
        if (!StringUtils.hasText(trafficVersion)) {
            //过滤所有没有压测流量的节点，即正常线上节点
            List<ServiceInstance> noneCanaryInstances = serviceInstances.stream()
                    .filter(e -> !e.getMetadata().containsKey(BalanceConstant.TRAFFIC_VERSION.getFlag()))
                    .collect(Collectors.toList());
            return getRoundRobinInstance(noneCanaryInstances);
        }

        //压测流量标记节点
        List<ServiceInstance> canaryInstancnes = serviceInstances.stream()
                .filter(e -> StringUtils.endsWithIgnoreCase(e.getMetadata().get(BalanceConstant.TRAFFIC_VERSION.getFlag()), trafficVersion))
                .collect(Collectors.toList());
        return getRoundRobinInstance(canaryInstancnes);
    }

    /**
     * 轮询调度（Round Robin Scheduling）算法就是以轮询的方式依次将请求调度不同的服务器，即每次调度执行i = (i + 1) mod n，
     * 并选出第i台服务器。算法的优点是其简洁性，它无需记录当前所有连接的状态，所以它是一种无状态调度。
     * 轮询调度算法的原理是每一次把来自用户的请求轮流分配给内部中的服务器，从1开始，直到N(内部服务器个数)，然后重新开始循环
     * <p>
     * 轮询调度算法假设所有服务器的处理性能都相同，不关心每台服务器的当前连接数和响应速度。当请求服务间隔时间变化比较大时，轮
     * 询调度算法容易导致服务器间的负载不平衡。
     * 所以此种均衡算法适合于服务器组中的所有服务器都有相同的软硬件配置并且平均服务请求相对均衡的情况
     */
    private Response<ServiceInstance> getRoundRobinInstance(List<ServiceInstance> instances) {
        //无可用节点，返空
        if (CollectionUtils.isEmpty(instances)) {
            log.warn("No servers available for service: " + serviceId);
            return new EmptyResponse();
        }

        int pos = Math.abs((this.position.incrementAndGet()));
        ServiceInstance instance = instances.get(pos % instances.size());
        return new DefaultResponse(instance);
    }
}
