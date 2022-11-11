package com.bushro.gateway.swagger;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.support.NameUtils;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
 * 聚合各个服务的swagger接口
 *
 * @author luo.qiang
 * @date 2022/11/08
 */

@Slf4j
@Component
public class SwaggerResourceConfig implements SwaggerResourcesProvider {

    @Resource
    private GatewayProperties gatewayProperties;

    /**
     * 网关路由
     */
    @Resource
    private RouteLocator routeLocator;

    /**
     * 网关应用名称
     */
    @Value("${spring.application.name}")
    private String self;

    /**
     * swagger2默认的url后缀
     */
    private static final String SWAGGER2URL = "v2/api-docs?group=%s";


    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        List<String> routes = new ArrayList<>();
        //获取所有路由的ID
        routeLocator.getRoutes().subscribe(route -> routes.add(route.getId()));
        //过滤出配置文件中定义的路由->过滤出Path Route Predicate->根据路径拼接成api-docs路径->生成SwaggerResource
        gatewayProperties.getRoutes().stream()
                .filter(routeDefinition -> routes.contains(routeDefinition.getId()))
                .forEach(route -> {
                    route.getPredicates().stream()
                            .filter(predicateDefinition -> ("Path").equalsIgnoreCase(predicateDefinition.getName())).forEach( predicateDefinition -> {
                                //拼接访问接口
                                String replace = predicateDefinition.getArgs().get(NameUtils.GENERATED_NAME_PREFIX + "0").replace("**", String.format(SWAGGER2URL, route.getId()));
                                resources.add(swaggerResource(route.getId(),replace));
                            });
                });

        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location) {
        log.info("name:{},location:{}", name, location);
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion("2.0");
        return swaggerResource;
    }



//    @Override
//    public List<SwaggerResource> get() {
//        List<SwaggerResource> resources = new ArrayList<>();
//        List<String> routeHosts = new ArrayList<>();
//        // 获取所有可用的host：serviceId
//        routeLocator.getRoutes().filter(route -> route.getUri().getHost() != null)
//                .filter(route -> !self.equals(route.getUri().getHost()))
//                .subscribe(route -> routeHosts.add(route.getUri().getHost()));
//
//        // 记录已经添加过的server，存在同一个应用注册了多个服务在nacos上
//        Set<String> dealed = new HashSet<>();
//        routeHosts.forEach(instance -> {
//            // 拼接url，样式为/serviceId/v2/api-info，当网关调用这个接口时，会自动通过负载均衡寻找对应的主机
//            String url = String.format(SWAGGER2URL, instance.toLowerCase(), instance.toLowerCase());
//            // 无group时 使用默认
//            if (!dealed.contains(url)) {
//                dealed.add(url);
//                SwaggerResource swaggerResource = new SwaggerResource();
//                swaggerResource.setUrl(url);
//                swaggerResource.setName(instance);
//                // 服务下拉列表读取映射
//                resources.add(swaggerResource);
//            }
//        });
//        return resources;
//    }

}
