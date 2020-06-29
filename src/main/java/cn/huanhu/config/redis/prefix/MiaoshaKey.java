package cn.huanhu.config.redis.prefix;

/**
 * @author m
 * @className MiaoshaKey
 * @description MiaoshaKey
 * @date 2020/6/18
 */
public class MiaoshaKey extends BasePrefix{

    private MiaoshaKey(int expireSeconds,String prefix) {
        super(expireSeconds,prefix);
    }

    public static MiaoshaKey isGoodsOver = new MiaoshaKey(0,"go");
    public static MiaoshaKey getMiaoshaPath = new MiaoshaKey(60,"mp");
    public static MiaoshaKey getMiaoshaVerifyCode = new MiaoshaKey(180,"vc");


}
