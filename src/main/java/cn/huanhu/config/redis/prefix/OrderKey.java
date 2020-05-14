package cn.huanhu.config.redis.prefix;

/**
 * @author m
 * @className OrderKey
 * @description OrderKey
 * @date 2020/5/13
 */
public class OrderKey extends BasePrefix {

    private OrderKey(String prefix) {
        super(prefix);
    }

    public static OrderKey orderKey = new OrderKey("orderInfo");
}
