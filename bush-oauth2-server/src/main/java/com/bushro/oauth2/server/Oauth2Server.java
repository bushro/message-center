package com.bushro.oauth2.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * oauth2服务器
 *
 * @author luo.qiang
 * @date 2022/11/07
 */
@MapperScan("com.bushro.oauth2.server.mapper")
@ComponentScan(basePackages = {"com.bushro"})
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients(basePackages = {"com.bushro"})
public class Oauth2Server {

    public static void main(String[] args) {
        SpringApplication.run(Oauth2Server.class, args);
    }

}
