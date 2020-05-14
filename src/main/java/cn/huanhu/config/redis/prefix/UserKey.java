package cn.huanhu.config.redis.prefix;

/**
 * @author m
 * @className UserKey
 * @description 用户redis存储
 * @date 2020/5/12
 */
public class UserKey extends BasePrefix{

    public UserKey(String prefix) {
        super(prefix);
    }

    public UserKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static UserKey getById=new UserKey("userId");
    public static UserKey getByName=new UserKey("userName");
}
