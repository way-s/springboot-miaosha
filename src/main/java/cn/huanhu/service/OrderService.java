package cn.huanhu.service;

import cn.huanhu.dao.OrderDao;
import cn.huanhu.entity.MiaoshaOrder;
import cn.huanhu.entity.MiaoshaUser;
import cn.huanhu.entity.OrderInfo;
import cn.huanhu.entity.vo.GoodsVO;
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

    @Resource
    private OrderDao orderDao;

    public MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(Long userId, long goodsId) {
        return orderDao.getMiaoshaOrderByUserIdGoodsId(userId, goodsId);
    }

    @Transactional
    public OrderInfo createOrder(MiaoshaUser user, GoodsVO goodsVO) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCreateDate(new Date());
        orderInfo.setDeliveryAddrId(0L);
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsId(goodsVO.getId());
        orderInfo.setGoodsName(goodsVO.getGoodsName());
        orderInfo.setGoodsPrice(goodsVO.getMiaoshaPrice());
        orderInfo.setOrderChannel(1);
        orderInfo.setStatus(0);
        long orderId = orderDao.insert(orderInfo);

        MiaoshaOrder miaoshaOrder = new MiaoshaOrder();
        miaoshaOrder.setGoodsId(goodsVO.getId());
        miaoshaOrder.setUserId(user.getId());
        miaoshaOrder.setOrderId(orderInfo.getId());
        orderDao.insertMiaoshaOrder(miaoshaOrder);

        return orderInfo;
    }
}
