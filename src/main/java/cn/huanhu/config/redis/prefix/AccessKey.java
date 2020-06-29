package cn.huanhu.config.redis.prefix;

/**
 * @author m
 * @className AccessKey
 * @description AccessKey
 * @date 2020/6/28
 */
public class AccessKey extends BasePrefix{

    public AccessKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static AccessKey withExpire(int expireSeconds){
        return new AccessKey(expireSeconds,"access");
    }
}
