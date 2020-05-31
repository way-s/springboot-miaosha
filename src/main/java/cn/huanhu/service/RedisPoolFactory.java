package cn.huanhu.service;

import cn.huanhu.config.redis.RedisConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author m
 * @className RedisPoolFactory
 * @description RedisPoolFactory
 * @date 2020/5/17
 */
@Component
public class RedisPoolFactory {

    private static  final Logger logger = LoggerFactory.getLogger(RedisPoolFactory.class);

    @Autowired
    RedisConfig redisConfig;

//    JedisExhaustedPoolException  Could not get a resource since the pool is exhausted
//    spring.redis.jedis.pool.max-active=10
//    spring.redis.jedis.pool.max-idle=10
//    spring.redis.jedis.pool.max-wait=-1
//    spring.redis.jedis.pool.min-idle=1
    @Bean
    public JedisPool jedisFactory() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(10);
        poolConfig.setMaxTotal(10);
        poolConfig.setMinIdle(redisConfig.getPoolMinIdle());
        poolConfig.setMaxWaitMillis(10 * 1000);
        logger.info("poolConfig:"+poolConfig.toString());
        return new JedisPool(poolConfig, redisConfig.getHost(),
                redisConfig.getPort(), redisConfig.getTimeout() * 1000, redisConfig.getPassword(), redisConfig.getDatabase());

    }


}
