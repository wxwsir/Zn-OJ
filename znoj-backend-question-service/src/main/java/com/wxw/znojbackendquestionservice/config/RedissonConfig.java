package com.wxw.znojbackendquestionservice.config;

import lombok.Data;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wxw
 * Redisson配置
 */
@Configuration
@ConfigurationProperties(prefix = "spring.redis")
@Data
public class RedissonConfig {

    private String host;
    private String port;

    @Bean
    public RedissonClient redisClient(){
        Config redissonConfig = new Config();
        String address = String.format("redis://%s:%s",host,port);
        redissonConfig.useSingleServer().setAddress(address).setDatabase(0);
        return Redisson.create(redissonConfig);
    }
}
