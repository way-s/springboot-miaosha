package cn.huanhu.config.redis.prefix;

/**
 * @ClassName: KeyPrefix
 * @Description: KeyPrefix
 * @author: m
 * @Date: 2020/5/12
 */
public interface KeyPrefix {
    /**
     * 过期时间 s
     * @return expireSeconds
     */
    public int expireSeconds();

    /**
     *  设置过期时间 s
     * @param expireSeconds
     */
    public void setExpireSeconds(int expireSeconds);

    /**
     * getPrefix
     * @return string
     */
    public String getPrefix();
}
