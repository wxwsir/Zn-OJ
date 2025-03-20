package com.wxw.znojbackenduserservice.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis Plus 配置
 *
 */
@Configuration
@MapperScan("com.wxw.znojbackenduserservice.mapper")
public class MyBatisPlusConfig {

    /**
     * 拦截器配置
     * 配置作用: 当 Mybatis-Plus 应用程序执行查询操作时，如果查询是分页查询，
     * PaginationInnerInterceptor会自动处理 SQL 语句，使其能够正确地执行分页
     * @return
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 分页插件
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
}