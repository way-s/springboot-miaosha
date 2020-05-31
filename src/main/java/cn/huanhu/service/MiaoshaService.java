package cn.huanhu.service;

import cn.huanhu.dao.MiaoshaDao;
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
    private MiaoshaDao miaoshaDao;

    @Resource
    private GoodsService goodsService;

    @Resource
    private OrderService orderService;

    @Transactional
    public OrderInfo miaosha(MiaoshaUser user, GoodsVO goodsVO) {
        //减库存下订单 写入秒杀订单
        goodsService.reduceStock(goodsVO);
        //
        return orderService.createOrder(user,goodsVO);
    }

}
