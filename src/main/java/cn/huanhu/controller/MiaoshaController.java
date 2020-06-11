package cn.huanhu.controller;

import cn.huanhu.entity.MiaoshaUser;
import cn.huanhu.entity.OrderInfo;
import cn.huanhu.entity.vo.GoodsVO;
import cn.huanhu.service.GoodsService;
import cn.huanhu.service.MiaoshaService;
import cn.huanhu.service.OrderService;
import cn.huanhu.service.RedisService;
import cn.huanhu.utils.result.CodeMsg;
import cn.huanhu.utils.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author m
 * @className MiaoshaController
 * @description MiaoshaController
 * @date 2020/5/30
 */

@Controller
@Api(value = "秒杀接口")
@RequestMapping(value = "miaosha/")
public class MiaoshaController {

    private static final Logger logger = LoggerFactory.getLogger(MiaoshaController.class);

    @Autowired
    private RedisService redisService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private MiaoshaService miaoshaService;

    /**
     * 秒杀
     * @param model
     * @param user
     * @param goodsId
     * @return
     */
    @ApiOperation(httpMethod = "POST",value = "秒杀",notes = "立即秒杀")
    @ApiImplicitParam(name = "id",value = "商品id",required = true ,dataType = "Long")
    @RequestMapping(value = "do_miaosha",method = RequestMethod.POST)
    @ResponseBody
    public Result<OrderInfo> doSha(Model model, MiaoshaUser user,
                        @RequestParam("goodsId") long goodsId) {
        logger.info("goodsId:"+goodsId);
        model.addAttribute("user", user);
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
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
    }
}
