package com.wxw.znojbackendquestionservice.config;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class RedisLimiterManager {

    @Resource
    private RedissonClient redissonClient;

    /**
     * 限流操作
     *
     * @param key 区分不同的限流器，比如不同的用户 id 应该分别统计
     */
    public boolean doRateLimit(String key) {
        // 创建一个限流器
        RRateLimiter rateLimiter = redissonClient.getRateLimiter(key);
        // 每秒最多访问 2 次
        // 参数1 type：限流类型  RateType.OVERALL 整体限流
        // 参数2 rate：限流速率，即单位时间内允许通过的请求数量
        // 参数3 rateInterval：限流时间间隔，即限流速率的计算周期长度
        // 参数4 unit：限流时间间隔单位
        boolean trySetRate = rateLimiter.trySetRate(RateType.OVERALL, 5, 1, RateIntervalUnit.SECONDS);
        if (trySetRate) {
            log.info("init rate = {}, interval = {}", rateLimiter.getConfig().getRate(), rateLimiter.getConfig().getRateInterval());
        }
        // 每个操作请求一个令牌：在限流速率内，返回true；若请求大于限流速率，则返回false
        boolean canOp = rateLimiter.tryAcquire(1);
        return canOp;
    }
}
