package cn.huanhu.service;

import cn.huanhu.config.rabbitmq.MqConfig;
import cn.huanhu.entity.MiaoshaOrder;
import cn.huanhu.entity.MiaoshaUser;
import cn.huanhu.entity.vo.GoodsVO;
import cn.huanhu.entity.vo.MiaoshaMessaage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author m
 * @className MQReceiver
 * @description RabbitMQReceiver
 * @date 2020/6/16
 */
@Service
public class MqReceiver {

    private static final Logger log = LoggerFactory.getLogger(MqReceiver.class);

    @Autowired
    private RedisService redisService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private MiaoshaService miaoshaService;


    @RabbitListener(queues = MqConfig.MIAOSHA_QUEUE)
    public void miaoshaReceive(String message) {
        log.info("receive message:" + message);
        MiaoshaMessaage mm = RedisService.stringToBean(message, MiaoshaMessaage.class);
        assert mm != null;
        MiaoshaUser user = mm.getUser();
        long goodsId = mm.getGoodsId();

        //商品信息
        GoodsVO goodsVO = goodsService.getGoodsVoByGoodsId(goodsId);
        //判断库存
        int stock = goodsVO.getStockCount();
        if (stock <= 0) {
            return;
        }
        //判断是否已经秒杀到了
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if (order != null) {
            log.info("已经秒杀成功");
            return;
        }

        //减库存 生成订单 写入秒杀订单
        miaoshaService.miaosha(user, goodsVO);
    }


    @RabbitListener(queues = MqConfig.QUEUE)
    public void receive(String message) {
        log.info("receive message:" + message);
    }

    @RabbitListener(queues = MqConfig.TOPIC_QUEUE1)
    public void receiveTopic1(String message) {
        log.info("receive topic1 message:" + message);
    }

    @RabbitListener(queues = MqConfig.TOPIC_QUEUE2)
    public void receiveTopic2(String message) {
        log.info("receive topic2 message:" + message);
    }

    @RabbitListener(queues = MqConfig.HEADER_QUEUE)
    public void receiveHeader(byte[] message) throws Exception {
        log.info("receive Header message:" + new String(message));
    }
}
