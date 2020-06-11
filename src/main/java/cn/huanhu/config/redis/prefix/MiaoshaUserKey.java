package cn.huanhu.config.redis.prefix;

/**
 * @author m
 * @className MiaoshaUserKey
 * @description MiaoshaUserKey
 * @date 2020/5/17
 */
public class MiaoshaUserKey extends BasePrefix{

    private static final int TOKEN_EXPIRE = (1800);

    private static final int CODE_EXPIRE = (60);

    public MiaoshaUserKey(String prefix) {
        super(prefix);
    }

    public MiaoshaUserKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static MiaoshaUserKey token = new MiaoshaUserKey(TOKEN_EXPIRE,"tk");

    public static MiaoshaUserKey verCode = new MiaoshaUserKey(CODE_EXPIRE,"VC");

    public static MiaoshaUserKey getById = new MiaoshaUserKey(0,"id");

}
