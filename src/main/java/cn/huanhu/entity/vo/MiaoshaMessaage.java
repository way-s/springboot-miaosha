package cn.huanhu.entity.vo;

import cn.huanhu.entity.MiaoshaUser;

import java.io.Serializable;

/**
 * @author m
 * @className MiaoshaMessaage
 * @description MiaoshaMessaage
 * @date 2020/6/18
 */
public class MiaoshaMessaage implements Serializable {

    private MiaoshaUser user;

    private long goodsId;

    public MiaoshaUser getUser() {
        return user;
    }

    public void setUser(MiaoshaUser user) {
        this.user = user;
    }

    public long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }
}
