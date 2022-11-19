package com.bushro.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * 认证服务器
 *
 * @author luo.qiang
 * @date 2022/11/07
 */
@MapperScan("com.bushro.**.mapper")
@ComponentScan(basePackages = {"com.bushro"})
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients(basePackages = {"com.bushro"})
public class AuthServer {

    public static void main(String[] args) {
        SpringApplication.run(AuthServer.class, args);
    }

}
