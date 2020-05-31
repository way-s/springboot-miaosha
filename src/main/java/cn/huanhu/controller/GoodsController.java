package cn.huanhu.controller;

import cn.huanhu.entity.MiaoshaUser;
import cn.huanhu.entity.vo.GoodsVO;
import cn.huanhu.service.GoodsService;
import cn.huanhu.service.MiaoshaUserService;
import cn.huanhu.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author m
 * @className GoodsController
 * @description GoodsController
 * @date 2020/5/17
 */
@CrossOrigin
@Controller
@RequestMapping(value = "goods/")
public class GoodsController {

    private static final Logger logger = LoggerFactory.getLogger(GoodsController.class);

    @Autowired
    MiaoshaUserService miaoshaUserService;

    @Autowired
    RedisService redisService;

    @Autowired
    GoodsService goodsService;

    @RequestMapping(value = "toList")
    public String toLogin(Model model, MiaoshaUser user) {
        model.addAttribute("user", user.toString());
        List<GoodsVO> goodsList = goodsService.listGoodsVo();
        model.addAttribute("goodsList", goodsList);
        return "goodsList";
    }

    @RequestMapping("toDetail/{id}")
    public String toDetail(Model model, MiaoshaUser user,
                           @PathVariable("id") long id) {
//snowflake
        logger.info("id:" + id);
        GoodsVO goodsVO = goodsService.getGoodsVoByGoodsId(id);
        model.addAttribute("goods", goodsVO);
        model.addAttribute("user", user.toString());
        logger.info("goods:" + goodsVO);
        //开始结束时间
        long startAt = goodsVO.getStartDate().getTime();
        long endAt = goodsVO.getEndDate().getTime();
        long now = System.currentTimeMillis();
        logger.info("now:"+now+"\t"+"startAt:"+startAt+"\t"+"endAt:"+endAt);
        //开始状态
        int miaoshaStatus = 0;
        //时间
        int remainSeconds = 0;
        //秒杀倒计时 开始 结束 进行中
        if (now < startAt) {
            remainSeconds = (int) ((startAt - now) / 1000);
        } else if (now > endAt) {
            miaoshaStatus = 2;
            remainSeconds = -1;
        } else {
            miaoshaStatus = 1;
        }
        logger.info("miaoshaStatus:" + miaoshaStatus + "\t" + "remainSeconds:" + remainSeconds);
        model.addAttribute("miaoshaStatus", miaoshaStatus);
        model.addAttribute("remainSeconds", remainSeconds);
        return "goodsDetail";
    }

}
