package cn.huanhu.entity.vo;

import cn.huanhu.entity.OrderInfo;

/**
 * @author m
 * @className OrderDetailVo
 * @description OrderDetailVo
 * @date 2020/6/8
 */
public class OrderDetailVo {

    private GoodsVO goods;

    private OrderInfo order;

    public GoodsVO getGoods() {
        return goods;
    }

    public void setGoods(GoodsVO goods) {
        this.goods = goods;
    }

    public OrderInfo getOrder() {
        return order;
    }

    public void setOrder(OrderInfo order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "OrderDetailVo{" +
                "goods=" + goods +
                ", order=" + order +
                '}';
    }
}
