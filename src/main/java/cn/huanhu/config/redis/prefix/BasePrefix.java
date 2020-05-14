package cn.huanhu.config.redis.prefix;

/**
 * @author m
 * @className BasePrefix
 * @description BasePrefix
 * @date 2020/5/12
 */
public abstract class BasePrefix implements KeyPrefix{

    private int expireSeconds;

    private String preFix;

    /**
     * 0代表永不过期
     */
    public BasePrefix(String prefix){
        this(0,prefix);
    }

    public BasePrefix(int expireSeconds, String prefix) {
        this.expireSeconds = expireSeconds;
        this.preFix = prefix;
    }


    /**
     * 0代表永不过期
     */
    @Override
    public int expireSeconds() {
        return expireSeconds;
    }

    /**
     * 设置过期时间 s
     * @param expireSeconds
     */
    @Override
    public void setExpireSeconds(int expireSeconds) {
        this.expireSeconds = expireSeconds;
    }

    /**
     * 获取前缀
     * @return preFix
     */
    @Override
    public String getPreFix() {
        String className = getClass().getSimpleName();
        return className + ":" + preFix;
    }
}
