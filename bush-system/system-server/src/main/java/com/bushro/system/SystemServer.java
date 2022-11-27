package com.bushro.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;

@MapperScan("com.bushro.**.mapper")
@ComponentScan(basePackages = "com.bushro")
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients(basePackages = {"com.bushro"})
public class SystemServer {

    @Autowired
    private Environment env;

    public static void main(String[] args) {
        SpringApplication.run(SystemServer.class, args);
    }
}

