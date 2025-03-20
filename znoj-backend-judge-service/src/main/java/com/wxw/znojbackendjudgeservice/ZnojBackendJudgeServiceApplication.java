package com.wxw.znojbackendjudgeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

import static com.wxw.znojbackendjudgeservice.rabbitmq.InitRabbitMqBean.init;

@SpringBootApplication
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@ComponentScan("com.wxw")
@EnableDiscoveryClient
// 扫描指定的包并自动注册 Feign 客户端接口(innerConrtoller 与 FeignClient)
@EnableFeignClients(basePackages = {"com.wxw.znojbackendserviceclient.service"})
public class ZnojBackendJudgeServiceApplication {

    public static void main(String[] args) {
        init();
        SpringApplication.run(ZnojBackendJudgeServiceApplication.class, args);
    }

}
