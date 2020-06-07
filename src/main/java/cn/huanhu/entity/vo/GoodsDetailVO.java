package cn.huanhu.entity.vo;

import cn.huanhu.entity.MiaoshaUser;
import lombok.Data;

import java.io.Serializable;

/**
 * @author m
 * @className GoodsDetailVO
 * @description GoodsDetailVO
 * @date 2020/6/5
 */
@Data
public class GoodsDetailVO implements Serializable {
    //秒杀状态
    private int miaoshaStatus = 0;
    //剩余时间时间
    private int remainSeconds = 0;

    private GoodsVO goods;

    private MiaoshaUser user;

    public int getMiaoshaStatus() {
        return miaoshaStatus;
    }

    public void setMiaoshaStatus(int miaoshaStatus) {
        this.miaoshaStatus = miaoshaStatus;
    }

    public int getRemainSeconds() {
        return remainSeconds;
    }

    public void setRemainSeconds(int remainSeconds) {
        this.remainSeconds = remainSeconds;
    }

    public GoodsVO getGoods() {
        return goods;
    }

    public void setGoods(GoodsVO goods) {
        this.goods = goods;
    }

    public MiaoshaUser getUser() {
        return user;
    }

    public void setUser(MiaoshaUser user) {
        this.user = user;
    }


}
