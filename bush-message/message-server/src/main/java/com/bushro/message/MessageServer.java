package com.bushro.message;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @description: DOTO
 * @author: luoq
 * @date: 2022/9/27
 */
@MapperScan("com.bushro.*.mapper")
@ComponentScan(basePackages = "com.bushro.*")
@EnableDiscoveryClient
@SpringBootApplication
public class MessageServer {

    public static void main(String[] args) {
        SpringApplication.run(MessageServer.class, args);
    }
}
