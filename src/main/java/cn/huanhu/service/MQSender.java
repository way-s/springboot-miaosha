package cn.huanhu.service;


import cn.huanhu.config.rabbitmq.MqConfig;
import cn.huanhu.entity.vo.MiaoshaMessaage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author m
 * @className MQSender
 * @description RabbitMQSender
 * @date 2020/6/16
 */
@Service
public class MqSender {

    private static final Logger log = LoggerFactory.getLogger(MqSender.class);

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendMiaoshaMessage(MiaoshaMessaage mm) {
        String msg = RedisService.beanToString(mm);
        log.info("send message"+msg);
        amqpTemplate.convertAndSend(MqConfig.MIAOSHA_QUEUE,msg);
    }





    public void send(Object message){
        String msg = RedisService.beanToString(message);
        log.info("send message"+msg);
        amqpTemplate.convertAndSend(MqConfig.QUEUE,msg);

    }

    public void sendTopic(Object message){
        String msg = RedisService.beanToString(message);
        log.info("send topic message:"+msg);
        amqpTemplate.convertAndSend(MqConfig.TOPIC_EXCHANGE,"topic.key1",msg+"1");
        amqpTemplate.convertAndSend(MqConfig.TOPIC_EXCHANGE,"topic.key2",msg+"2");
    }

    public void sendFanout(Object message){
        String msg = RedisService.beanToString(message);
        log.info("send fanout message:"+msg);
        amqpTemplate.convertAndSend(MqConfig.FANOUT_EXCHANGE,"topic.key1",msg+"1");
        amqpTemplate.convertAndSend(MqConfig.FANOUT_EXCHANGE,"topic.key2",msg+"2");
    }

    public void sendHeader(Object message){
        String msg = RedisService.beanToString(message);
        log.info("send header message:"+msg);
        MessageProperties properties = new MessageProperties();
        properties.setHeader("header1","v1");
        properties.setHeader("header2","v2");
        Message obj = new Message(msg.getBytes(),properties);
        amqpTemplate.convertAndSend(MqConfig.HEADERS_EXCHANGE,"topic.key1",obj);
    }


}
