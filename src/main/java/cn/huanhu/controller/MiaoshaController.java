package cn.huanhu.controller;

import cn.huanhu.entity.MiaoshaOrder;
import cn.huanhu.entity.MiaoshaUser;
import cn.huanhu.entity.OrderInfo;
import cn.huanhu.entity.vo.GoodsVO;
import cn.huanhu.service.GoodsService;
import cn.huanhu.service.MiaoshaService;
import cn.huanhu.service.OrderService;
import cn.huanhu.service.RedisService;
import cn.huanhu.utils.result.CodeMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author m
 * @className MiaoshaController
 * @description MiaoshaController
 * @date 2020/5/30
 */

@Controller
@RequestMapping(value = "miaosha/")
public class MiaoshaController {

    private static final Logger logger = LoggerFactory.getLogger(GoodsController.class);

    @Autowired
    private RedisService redisService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private MiaoshaService miaoshaService;

    @RequestMapping("do_miaosha")
    public String doSha(Model model, MiaoshaUser user,
                        @RequestParam("goodsId") long goodsId) {
        model.addAttribute("user", user);
        if (user == null) {
            return "login";
        }
        //判断库存
        GoodsVO goodsVO = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goodsVO.getStockCount();
        if (stock <= 0) {
            model.addAttribute("errmsg", CodeMsg.MIAOSHA_OVER.getMsg());
            return "miaosha_fail";
        }
        //判断是否已经秒杀到了
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if (order != null) {
            model.addAttribute("errmsg", CodeMsg.MIAOSHA_REPET.getMsg());
            return "miaosha_fail";
        }
        //减少库存 下订单 写入秒杀订单
        OrderInfo orderInfo = miaoshaService.miaosha(user, goodsVO);
        model.addAttribute("orderInfo", orderInfo);
        model.addAttribute("goods",goodsVO);
        logger.info("goods:"+goodsVO+"\t"+"orderInfo:"+orderInfo);
        return "order_detail";
    }
}
