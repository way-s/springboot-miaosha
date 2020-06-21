package cn.huanhu.service;

import cn.huanhu.config.redis.prefix.OrderKey;
import cn.huanhu.dao.OrderDao;
import cn.huanhu.entity.MiaoshaOrder;
import cn.huanhu.entity.MiaoshaUser;
import cn.huanhu.entity.OrderInfo;
import cn.huanhu.entity.vo.GoodsVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author m
 * @className OrderService
 * @description OrderService
 * @date 2020/5/30
 */
@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Resource
    private OrderDao orderDao;

    @Autowired
    private RedisService redisService;

    /**
     * 根据用户id和商品id 拿到秒杀订单信息
     * @param userId
     * @param goodsId
     * @return
     */
    public MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(Long userId, long goodsId) {
//        return orderDao.getMiaoshaOrderByUserIdGoodsId(userId, goodsId);
        return redisService.get(OrderKey.orderKey, ""+userId+"_" + goodsId, MiaoshaOrder.class);
    }

    /**
     * 秒杀订单详细信息
     * @param orderId
     * @return
     */
    public OrderInfo getOrderById(long orderId) {
        return orderDao.getOrderById(orderId);
    }

    /**
     * 创建订单
     * @param user
     * @param goodsVO
     * @return
     */
    @Transactional
    public OrderInfo createOrder(MiaoshaUser user, GoodsVO goodsVO) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCreateDate(new Date());
        orderInfo.setDeliveryAddrId(0L);
        orderInfo.setGoodsCount(1);
        orderInfo.setUserId(user.getId());
        orderInfo.setGoodsId(goodsVO.getId());
        orderInfo.setGoodsName(goodsVO.getGoodsName());
        orderInfo.setGoodsPrice(goodsVO.getMiaoshaPrice());
        orderInfo.setOrderChannel(1);
        orderInfo.setStatus(0);
        // 插入秒杀订单详细信息 返回订单id
        orderDao.insert(orderInfo);

        logger.info("orderInfo:" + orderInfo.toString());
        MiaoshaOrder miaoshaOrder = new MiaoshaOrder();
        miaoshaOrder.setGoodsId(goodsVO.getId());
        miaoshaOrder.setUserId(user.getId());
        miaoshaOrder.setOrderId(orderInfo.getId());
        // 插入秒杀的商品表
        orderDao.insertMiaoshaOrder(miaoshaOrder);
        //将订单数据放入缓存
        redisService.set(OrderKey.orderKey,""+user.getId()+"_" +goodsVO.getId(), miaoshaOrder);
        return orderInfo;
    }


}
