package cn.huanhu.service;

import cn.huanhu.config.redis.prefix.MiaoshaKey;
import cn.huanhu.entity.MiaoshaOrder;
import cn.huanhu.entity.MiaoshaUser;
import cn.huanhu.entity.OrderInfo;
import cn.huanhu.entity.vo.GoodsVO;
import cn.huanhu.utils.Md5Utils;
import cn.huanhu.utils.UUIDUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * @author m
 * @className MiaoshaService
 * @description MiaoshaService
 * @date 2020/5/30
 */
@Service
public class MiaoshaService {

    private static final Logger log = LoggerFactory.getLogger(MiaoshaService.class);

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

    public boolean checkPath(MiaoshaUser user, long goodsId, String path) {
        if(user == null || path == null){
            return false;
        }
        String pathOld = redisService.get(MiaoshaKey.getMiaoshaPath,""+user.getId() + "_"+ goodsId,String.class);
        return path.equals(pathOld);
    }

    public String createMiaoshaPath(MiaoshaUser user, long goodsId) {
        String str = Md5Utils.md5(UUIDUtils.uuid()+"123456");
        redisService.set(MiaoshaKey.getMiaoshaPath,""+user.getId() + "_"+ goodsId,str);
        return str;
    }

    /**
     * 创建图形验证码
     * @param user
     * @param goodsId
     * @return
     */
    public BufferedImage createVerifyCode(MiaoshaUser user, long goodsId) {
        if (user == null || goodsId <= 0) {
            return null;
        }
        int width = 80;
        int height = 32;
        // 创建图片
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        // 设置背景色
        g.setColor(new Color(0xDCDCDC));
        // 填充矩形
        g.fillRect(0, 0, width, height);
        // 划定边界
        g.setColor(Color.black);
        // 绘制矩形
        g.drawRect(0, 0, width - 1, height - 1);
        // 创建一个随机实例来生成代码
        Random rdm = new Random();
        // 造成一些混乱  创建50个干扰点
        for (int i = 0; i < 50; i++) {
            int x = rdm.nextInt(width);
            int y = rdm.nextInt(height);
            g.drawOval(x, y, 0, 0);
        }
        // 产生随机码
        String verifyCode = generateVerifyCode(rdm);
        // 设置颜色
        g.setColor(new Color(0, 100, 0));
        // 设置字体
        g.setFont(new Font("Candara", Font.BOLD, 24));
        // 画线
        g.drawString(verifyCode, 8, 24);
        g.dispose();
        //把验证码存到redis中
        int rnd = calc(verifyCode);
        redisService.set(MiaoshaKey.getMiaoshaVerifyCode, user.getId() + "," + goodsId, rnd);
        //输出图片
        return image;
    }

    private int calc(String exp) {
        try{
            // ScriptEngineManager 脚本引擎管理器
            ScriptEngineManager manager = new ScriptEngineManager();
            //按名称获取引擎
            ScriptEngine engine = manager.getEngineByName("JavaScript");
            return (Integer)engine.eval(exp);
        }catch (Exception e){
            log.info("calc:"+e.getMessage());
            return 0;
        }
    }

    private static final char[] OPS = new char[]{'+','-','*'};

    /**
     * 随机码 + - *
     * @param rdm
     * @return
     */
    private String generateVerifyCode(Random rdm) {
        int num1 = rdm.nextInt(10);
        int num2 = rdm.nextInt(10);
        int num3 = rdm.nextInt(10);
        char op1 = OPS[rdm.nextInt(3)];
        char op2 = OPS[rdm.nextInt(3)];
        //返回拼接
        return "" + num1 + op1 + num2 + op2 + num3;
    }

    /**
     * 效验验证码
     * @param user
     * @param goodsId
     * @param verifyCode
     * @return
     */
    public boolean checkVerifyCode(MiaoshaUser user, long goodsId, int verifyCode) {
        if (user == null || goodsId <= 0) {
            return false;
        }
        Integer codeOId = redisService.get(MiaoshaKey.getMiaoshaVerifyCode, user.getId() + "," + goodsId,Integer.class);
        if(codeOId == null || codeOId - verifyCode != 0){
            return false;
        }
        redisService.delete(MiaoshaKey.getMiaoshaVerifyCode, user.getId() + "," + goodsId);
        return true;
    }
}
