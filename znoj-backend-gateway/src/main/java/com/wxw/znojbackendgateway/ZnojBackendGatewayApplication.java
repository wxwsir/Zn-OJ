package com.wxw.znojbackendgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

// 排除数据源自动配置
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
// 开启服务发现功能----使得应用能够注册到服务注册中心nacos
@EnableDiscoveryClient
public class ZnojBackendGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZnojBackendGatewayApplication.class, args);
    }

}
