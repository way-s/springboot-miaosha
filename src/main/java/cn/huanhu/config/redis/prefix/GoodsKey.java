package cn.huanhu.config.redis.prefix;

/**
 * @author m
 * @className GoodsKey
 * @description GoodsKey
 * @date 2020/6/5
 */
public class GoodsKey extends BasePrefix{

    public GoodsKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    /**
     * 页面缓存时间为60秒
     */
    public static GoodsKey getGoodsList = new GoodsKey(60,"gl");
    public static GoodsKey getGoodsDetail = new GoodsKey(60,"gd");
    public static GoodsKey getMiaoshaGoodsStock = new GoodsKey(0,"gs");


}
