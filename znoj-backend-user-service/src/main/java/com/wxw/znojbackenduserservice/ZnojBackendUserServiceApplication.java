package com.wxw.znojbackenduserservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication()
@MapperScan("com.wxw.znojbackenduserservice.mapper")
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@ComponentScan("com.wxw")
@EnableDiscoveryClient
// 扫描指定的包并自动注册 Feign 客户端接口(innerConrtoller 与 FeignClient)
@EnableFeignClients(basePackages = {"com.wxw.znojbackendserviceclient.service"})
public class ZnojBackendUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZnojBackendUserServiceApplication.class, args);
    }

}
