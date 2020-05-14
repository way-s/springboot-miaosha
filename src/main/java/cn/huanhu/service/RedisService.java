package cn.huanhu.service;

import cn.huanhu.config.redis.prefix.KeyPrefix;

/**
 * @ClassName: RedisService
 * @Description:
 * @author: m
 * @Date: 2020/5/12
 */
public interface RedisService {

    /**
     * 获取单个对象
     * @param key key
     * @param clazz class
     * @param <T> class
     * @return t
     */
    public <T>T get(KeyPrefix keyPrefix, String key, Class<T> clazz);
    /**
     * 设置对象
     * @param keyPrefix
     * @param key
     * @param t
     * @param <T>
     * @return
     */
    public <T> boolean set(KeyPrefix keyPrefix, String key, T t);

    /**
     * 判断key 是否存在
     * @param keyPrefix
     * @param key
     * @param <T>
     * @return
     */
    public <T> boolean exist(KeyPrefix keyPrefix,String key);

    /**
     * 增加数值
     * @param keyPrefix keyPreFix
     * @param key
     * @param addNumber
     * @param <T> t
     * @return long
     */
    public <T> Long incr(KeyPrefix keyPrefix,String key,long addNumber);

    /**
     * 减少数值
     * @param keyPrefix
     * @param key
     * @param deNumber
     * @param <T>
     * @return
     */
    public <T> Long decrBy(KeyPrefix keyPrefix,String key,long deNumber);


}

