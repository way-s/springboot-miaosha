package cn.huanhu.service.impl;


import cn.huanhu.config.redis.prefix.KeyPrefix;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.thymeleaf.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;

/**
 * @author m
 * @className RedisServiceImpl
 * @description RedisServiceImpl
 * @date 2020/5/11
 */
@Service
public class RedisServiceImpl  {

    private static final Logger logger= LoggerFactory.getLogger(RedisServiceImpl.class);

    @Autowired
    private JedisPool jedisPool;

    /**
     * 获取单个对象
     * @param key key
     * @param clazz class
     * @param <T> class
     * @return t
     */
    public <T>T get(KeyPrefix keyPrefix, String key, Class<T> clazz){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            String realKey = keyPrefix.getPreFix() + key;
            String str = jedis.get(realKey);
            T t = stringToBean(str,clazz);
            return t;
        }catch (Exception e){
            e.printStackTrace();
            logger.info(e.toString());
            return null;
        }finally {
            returnJedis(jedis);
        }
    }

    /**
     * 设置对象
     * @param keyPrefix
     * @param key
     * @param t
     * @param <T>
     * @return
     */
    public <T> boolean set(KeyPrefix keyPrefix, String key, T t){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            //生成真正的key
            String realKey = keyPrefix.getPreFix() + key;
            String value = beanToString(t);
            if (StringUtils.isEmpty(value)){
                return false;
            }
            int expireSeconds = keyPrefix.expireSeconds();
            // <= 0 代表永不过期 否则包含过期时间
            if (expireSeconds <= 0){
                jedis.set(realKey, value);
            }else{
                jedis.setex(realKey,expireSeconds,value);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            returnJedis(jedis);
        }
    }

    /**
     * 判断key 是否存在
     * @param keyPrefix
     * @param key
     * @param <T>
     * @return
     */
    public <T> boolean exist(KeyPrefix keyPrefix, String key){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            String realKey = keyPrefix.getPreFix() + key;
            return jedis.exists(realKey);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            returnJedis(jedis);
        }
    }

    /**
     * 增加数值
     * @param keyPrefix keyPreFix
     * @param key
     * @param addNumber
     * @param <T> t
     * @return long
     */
    public <T> Long incr(KeyPrefix keyPrefix, String key, long addNumber){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            String realKey = keyPrefix.getPreFix() + key;
            if (addNumber < 1L){
                return jedis.incr(realKey);
            }
            return jedis.incrBy(realKey,addNumber);
        }catch (Exception e){
            e.printStackTrace();
            return 0L;
        }finally {
            returnJedis(jedis);
        }
    }

    /**
     * 减少数值
     * @param keyPrefix
     * @param key
     * @param deNumber
     * @param <T>
     * @return
     */
    public <T> Long decrBy(KeyPrefix keyPrefix, String key, long deNumber){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            String realKey = keyPrefix.getPreFix() + key;
            if (deNumber < 1L){
                return jedis.decr(realKey);
            }
            return jedis.decrBy(realKey,deNumber);
        }catch (Exception e){
            e.printStackTrace();
            return 0L;
        }finally {
            returnJedis(jedis);
        }
    }

    /**
     * 关闭连接
     * @param jedis
     */
    private void returnJedis(final Jedis jedis){
        if ( jedis != null){
            jedis.close();
        }
    }

    /**
     * Bean对象转化为String
     */
    public static  <T> String beanToString(T value){
        if (value == null){
            return null;
        }
        Class<?> clazz = value.getClass();
        if (clazz == int.class || clazz == Integer.class){
            return "" + value;
        }else if (clazz == String.class){
            return (String)value;
        }else if (clazz == long.class || clazz == Long.class){
            return "" + value;
        }else {
            return JSON.toJSONString(value);
        }
    }

    /**
     * 字符串转化为Bean对象
     */
    public static  <T> T stringToBean(String str, Class<T> clazz){
        if (StringUtils.isEmpty(str) || str.length()<=0 ||clazz == null ){
            return null;
        }
        if (clazz == int.class || clazz == Integer.class){
            return (T)Integer.valueOf(str);
        }else if (clazz == String.class){
            return (T)str;
        }else if (clazz == long.class || clazz == Long.class){
            return (T)Long.valueOf(str);
        }else {
            return JSON.toJavaObject(JSON.parseObject(str),clazz);
        }
    }


}
