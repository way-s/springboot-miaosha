package cn.huanhu.service;

import cn.huanhu.config.redis.prefix.MiaoshaKey;
import cn.huanhu.entity.MiaoshaOrder;
import cn.huanhu.entity.MiaoshaUser;
import cn.huanhu.entity.OrderInfo;
import cn.huanhu.entity.vo.GoodsVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author m
 * @className MiaoshaService
 * @description MiaoshaService
 * @date 2020/5/30
 */
@Service
public class MiaoshaService {

    @Resource
    private GoodsService goodsService;

    @Resource
    private OrderService orderService;

    @Resource
    private RedisService redisService;

    /**
     * 减库存 创建的订单
     * @param user
     * @param goodsVO
     * @return
     */
    @Transactional
    public OrderInfo miaosha(MiaoshaUser user, GoodsVO goodsVO) {
        //减库存下订单 写入秒杀订单
        boolean result = goodsService.reduceStock(goodsVO);
        if(result){
            //返回创建的订单
            return orderService.createOrder(user,goodsVO);
        }else {
            setGoodsOver(goodsVO.getId());
            return null;
        }
    }

    /**
     *秒杀结果
     *
     *成功 返回订单id
     *失败 返回 -1
     *排队中 返回 0
     * @param userId 用户id
     * @param goodsId 商品id
     * @return
     */
    public long getMiaoshaResult(Long userId, long goodsId) {
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(userId,goodsId);
        //秒杀成功
        if(order != null){
            return order.getOrderId();
        }else {
            boolean isOver = getGoodsOver(goodsId);
            if(isOver){
                return -1;
            }else {
                return 0;
            }
        }
    }

    private void setGoodsOver(Long goodsId) {
        redisService.set(MiaoshaKey.isGoodsOver,""+goodsId,true);
    }

    private boolean getGoodsOver(long goodsId) {
        return redisService.exist(MiaoshaKey.isGoodsOver,""+goodsId);
    }
}
