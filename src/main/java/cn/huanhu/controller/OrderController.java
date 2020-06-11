package cn.huanhu.controller;

import cn.huanhu.entity.MiaoshaUser;
import cn.huanhu.entity.OrderInfo;
import cn.huanhu.entity.vo.GoodsVO;
import cn.huanhu.entity.vo.OrderDetailVo;
import cn.huanhu.service.GoodsService;
import cn.huanhu.service.OrderService;
import cn.huanhu.service.RedisService;
import cn.huanhu.utils.result.CodeMsg;
import cn.huanhu.utils.result.Result;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author m
 * @className OrderController
 * @description OrderController
 * @date 2020/6/8
 */
@Controller
@Api(value = "订单")
@RequestMapping(value = "order/")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(MiaoshaController.class);

    @Autowired
    private RedisService redisService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private GoodsService goodsService;


    @RequestMapping("detail")
    @ResponseBody
    public Result<OrderDetailVo> info(
            Model model, MiaoshaUser user,
            @RequestParam("orderId") long orderId
    ) {
        //@NeedLogin
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        //秒杀订单详细信息
        OrderInfo order = orderService.getOrderById(orderId);
        if(order == null){
            //订单不存在
            return Result.error(CodeMsg.ORDER_NOT_EXIT);
        }
        //商品id
        long goodsId = order.getGoodsId();
        GoodsVO goods = goodsService.getGoodsVoByGoodsId(goodsId);
        OrderDetailVo vo = new OrderDetailVo();
        vo.setOrder(order);
        vo.setGoods(goods);
        return Result.success(vo);
    }
}
