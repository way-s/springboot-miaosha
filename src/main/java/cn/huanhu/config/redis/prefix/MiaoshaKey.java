package cn.huanhu.config.redis.prefix;

/**
 * @author m
 * @className MiaoshaKey
 * @description MiaoshaKey
 * @date 2020/6/18
 */
public class MiaoshaKey extends BasePrefix{

    private MiaoshaKey(String prefix) {
        super(prefix);
    }

    public static MiaoshaKey isGoodsOver = new MiaoshaKey("go");


}
