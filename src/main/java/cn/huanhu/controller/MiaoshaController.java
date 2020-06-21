package cn.huanhu.controller;

import cn.huanhu.config.redis.prefix.GoodsKey;
import cn.huanhu.entity.MiaoshaOrder;
import cn.huanhu.entity.MiaoshaUser;
import cn.huanhu.entity.vo.GoodsVO;
import cn.huanhu.entity.vo.MiaoshaMessaage;
import cn.huanhu.service.*;
import cn.huanhu.utils.result.CodeMsg;
import cn.huanhu.utils.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author m
 * @className MiaoshaController
 * @description MiaoshaController
 * @date 2020/5/30
 */

@Controller
@Api(value = "秒杀接口")
@RequestMapping(value = "miaosha/")
public class MiaoshaController implements InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(MiaoshaController.class);

    @Autowired
    private RedisService redisService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private MiaoshaService miaoshaService;

    @Autowired
    private MQSender mqSender;

    private Map<Long, Boolean> localOverMap = new HashMap<>();

    /**
     * 系统初始化，查询商品数量，加载到redis中
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        //查询所有商品
        List<GoodsVO> goodsVOList = goodsService.listGoodsVo();
        if (goodsVOList == null) {
            return;
        }
        for (GoodsVO goodsVO : goodsVOList) {
            //存放商品库存数量
            redisService.set(GoodsKey.getMiaoshaGoodsStock, "" + goodsVO.getId(), goodsVO.getStockCount());
            //没有结束 false
            localOverMap.put(goodsVO.getId(), false);
        }
    }

    /**
     * 秒杀优化
     * 1.系统初始化
     * 2.收到请求，预减库存
     * 3.请求入列
     * 4.请求出列，生成订单，更新数据库
     * 5.客户轮询，是否秒杀成功
     *
     * @param model
     * @param user
     * @param goodsId
     * @return
     */
    @ApiOperation(httpMethod = "POST", value = "秒杀", notes = "立即秒杀")
    @ApiImplicitParam(name = "id", value = "商品id", required = true, dataType = "Long")
    @RequestMapping(value = "do_miaosha", method = RequestMethod.POST)
    @ResponseBody
    public Result<Integer> doSha(Model model, MiaoshaUser user,
                                 @RequestParam("goodsId") long goodsId) {
        model.addAttribute("user", user);
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }

        //内存标记 减少redis访问
        boolean over = localOverMap.get(goodsId);
        if (over) {
            return Result.error(CodeMsg.MIAOSHA_OVER);
        }
        log.info("内存标记 ->");
        //预减库存 判断库存
        long stock = redisService.decrBy(GoodsKey.getMiaoshaGoodsStock, ""+goodsId);
        if (stock < 0) {
            localOverMap.put(goodsId, true);
            return Result.error(CodeMsg.MIAOSHA_OVER);
        }
        log.info("库存：" + stock);
        //判断是否已经秒杀到了
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if (order != null) {
            return Result.error(CodeMsg.MIAOSHA_REPEAT);
        }
        //入队
        MiaoshaMessaage mm = new MiaoshaMessaage();
        mm.setGoodsId(goodsId);
        mm.setUser(user);
        mqSender.sendMiaoshaMessage(mm);
        log.info("消息入列");
        //排队中
        return Result.success(0);

        /*
        //判断库存
        GoodsVO goodsVO = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goodsVO.getStockCount();
        if (stock <= 0) {
            //秒杀结束
            return Result.error(CodeMsg.MIAOSHA_OVER);
        }
        //判断是否已经秒杀到了
        OrderInfo order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if (order != null) {
            //秒杀重复
            return Result.error(CodeMsg.MIAOSHA_REPEAT);
        }
        //减少库存 下订单 写入秒杀订单
        OrderInfo orderInfo = miaoshaService.miaosha(user, goodsVO);
//        //返回生成的订单
//        model.addAttribute("orderInfo", orderInfo);
//        //返回商品信息
//        model.addAttribute("goods", goodsVO);
        logger.info("goods:" + goodsVO + "\t" + "orderInfo:" + orderInfo);
        return Result.success(orderInfo);

         */
    }

    /**
     * 查询是否生成订单
     * 成功 返回订单id
     * 失败 返回 -1
     * 排队中 返回 0
     *
     * @param model
     * @param user
     * @param goodsId
     * @return id 1 0
     */
    @RequestMapping(value = "result", method = RequestMethod.POST)
    @ResponseBody
    public Result<Long> doShaResult(Model model, MiaoshaUser user,
                                    @RequestParam("goodsId") long goodsId) {
        model.addAttribute("user", user);
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        //判断是否秒杀成功 秒杀成功返回订单id
        long result = miaoshaService.getMiaoshaResult(user.getId(), goodsId);
        log.info("result:"+result);
        return Result.success(result);
    }

}
