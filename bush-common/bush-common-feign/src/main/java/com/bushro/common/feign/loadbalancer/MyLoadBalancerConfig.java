package com.bushro.common.feign.loadbalancer;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * <p> LoadBalancer自定义负载均衡器 </p>
 *
 * @author luo.qiang
 * @description
 * @date 2022/11/16 22:56
 */
@Configuration
@LoadBalancerClients(defaultConfiguration = {MyLoadBalancerConfig.class})
public class MyLoadBalancerConfig {

//    @Bean
//    @LoadBalanced
//    public RestTemplate restTemplate() {
//        return new RestTemplate();
//    }

    @Bean
    public ReactorLoadBalancer<ServiceInstance> customLoadBalancer(Environment environment, LoadBalancerClientFactory loadBalancerClientFactory) {
        String serviceName = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
        return new CustomInstanceLoadBalancer(loadBalancerClientFactory.getLazyProvider(serviceName, ServiceInstanceListSupplier.class), serviceName);
    }

}
