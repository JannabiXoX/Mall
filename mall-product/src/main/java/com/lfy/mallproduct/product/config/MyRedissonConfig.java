package com.lfy.mallproduct.product.config;


import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @Author:feiyang
 * @Date:11/8/2023 9:25 AM
 */
@Configuration
public class MyRedissonConfig {

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redisson() throws IOException {
        // 创建配置
        Config config = new Config();
        config.useSingleServer().setAddress("redis://121.5.68.6:6379")
                .setPassword("Hq20010422@");
        // 根据配置创建一个RedissonClient实例
        RedissonClient redissonClient = Redisson.create(config);
        return redissonClient;
    }
}
