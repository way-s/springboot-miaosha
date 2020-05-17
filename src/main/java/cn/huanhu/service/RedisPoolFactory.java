package cn.huanhu.service;

import cn.huanhu.config.redis.RedisConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author m
 * @className RedisPoolFactory
 * @description RedisPoolFactory
 * @date 2020/5/17
 */
@Service
public class RedisPoolFactory {

    @Autowired
    RedisConfig redisConfig;

    @Bean
    public JedisPool jedisFactory() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
//        poolConfig.setMaxIdle(redisConfig.getPoolMaxIdle());
//        poolConfig.setMaxTotal(redisConfig.getPoolMaxActive());
//        poolConfig.setMaxWaitMillis(redisConfig.getPoolMaxWait() * 1000);
        return new JedisPool(poolConfig, redisConfig.getHost(),
                redisConfig.getPort(), redisConfig.getTimeout() * 1000, redisConfig.getPassword(), redisConfig.getDatabase());

    }


}
