package cn.huanhu.controller;

import cn.huanhu.config.redis.prefix.GoodsKey;
import cn.huanhu.entity.MiaoshaUser;
import cn.huanhu.entity.vo.GoodsDetailVO;
import cn.huanhu.entity.vo.GoodsVO;
import cn.huanhu.service.GoodsService;
import cn.huanhu.service.MiaoshaUserService;
import cn.huanhu.service.RedisService;
import cn.huanhu.utils.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author m
 * @className GoodsController
 * @description GoodsController
 * @date 2020/5/17
 */
@CrossOrigin
@Controller
@Api(value = "商品接口")
@RequestMapping(value = "goods/")
public class GoodsController {

    private static final Logger logger = LoggerFactory.getLogger(GoodsController.class);

    @Autowired
    MiaoshaUserService miaoshaUserService;

    @Autowired
    RedisService redisService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;

    /**
     * 商品列表接口
     *
     * @param model
     * @param user
     * @return .process()方法中ctx所在参数所需要的类型为接口IContext 需要有实现IContext的类就可以了
     * 在thymeleaf.spring5的API中把大部分的功能移到了IWebContext下面,用来区分边界。剔除了ApplicationContext 过多的依赖
     */
    @ApiOperation(httpMethod = "POST", value = "商品列表", notes = "查询所有商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "商品id", required = true, dataType = "Long")
    })
    @RequestMapping(value = "toList", produces = "text/html")
    @ResponseBody
    public String toList(
            HttpServletRequest request, HttpServletResponse response,
            Model model, MiaoshaUser user) {
        model.addAttribute("user", user.toString());
        List<GoodsVO> goodsList = goodsService.listGoodsVo();
        model.addAttribute("goodsList", goodsList);
        //取缓存
        String html = redisService.get(GoodsKey.getGoodsList, "", String.class);
        if (!StringUtils.isEmpty(html)) {
            return html;
        }
//        SpringWebContext ctx = new SpringWebContext(request,response,request.getServletContext(), request.getLocale(), model.asMap(), applicationContext);
        //优化访问速度，应对高并发，想把页面信息全部获取出来存到redis缓存中
        WebContext ctx = new WebContext(request, response,
                request.getServletContext(), request.getLocale(), model.asMap());
        //手动渲染
        html = thymeleafViewResolver.getTemplateEngine().process("goodsList", ctx);
        if (!StringUtils.isEmpty(html)) {
            redisService.set(GoodsKey.getGoodsList, "", html);
        }
        return html;
    }

    /**
     * 商品详情页面
     *
     * @param model
     * @param user
     * @param goodsId
     * @return
     */
    @ApiOperation(httpMethod = "POST", value = "商品详情", notes = "某一个商品详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goodsId", value = "商品id", required = true, dataType = "Long")
    })
    @RequestMapping(value = "toDetail/{goodsId}", produces = "text/html")
    @ResponseBody
    public String toDetail(
            HttpServletRequest request, HttpServletResponse response,
            Model model, MiaoshaUser user,
            @PathVariable("goodsId") long goodsId) {
//snowflake
        //取缓存
        String html = redisService.get(GoodsKey.getGoodsList, "" + goodsId, String.class);
        if (!StringUtils.isEmpty(html)) {
            return html;
        }
        logger.info("id:" + goodsId);
        GoodsVO goodsVO = goodsService.getGoodsVoByGoodsId(goodsId);
        model.addAttribute("goods", goodsVO);
        model.addAttribute("user", user.toString());
        logger.info("goods:" + goodsVO);
        //开始 结束 时间
        long startAt = goodsVO.getStartDate().getTime();
        long endAt = goodsVO.getEndDate().getTime();
        long now = System.currentTimeMillis();
        logger.info("now:" + now + "\t" + "startAt:" + startAt + "\t" + "endAt:" + endAt);
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
//        return "goodsDetail";

        //手动渲染
        WebContext ctx = new WebContext(request, response,
                request.getServletContext(), request.getLocale(), model.asMap());
        //手动渲染
        html = thymeleafViewResolver.getTemplateEngine().process("GoodsDetail", ctx);
        if (!StringUtils.isEmpty(html)) {
            redisService.set(GoodsKey.getGoodsDetail, "" + goodsId, html);
        }
        return html;
    }

    /**
     * 商品详情页面静态化
     */
    @ApiOperation(httpMethod = "POST", value = "商品详情", notes = "某一个商品详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goodsId", value = "商品id", required = true, dataType = "Long")
    })
    @RequestMapping(value = "to_detail2/{goodsId}")
    @ResponseBody
    public Result<GoodsDetailVO> toDetail2(
            HttpServletRequest request, HttpServletResponse response,
            Model model, MiaoshaUser user,
            @PathVariable("goodsId") long goodsId) {

        GoodsVO goodsVO = goodsService.getGoodsVoByGoodsId(goodsId);
        logger.info("-------goodsVO:" + goodsVO);
        //开始 结束 时间
        long startAt = goodsVO.getStartDate().getTime();
        long endAt = goodsVO.getEndDate().getTime();
        long now = System.currentTimeMillis();
        logger.info("now:" + now + "\t" + "startAt:" + startAt + "\t" + "endAt:" + endAt);
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
        GoodsDetailVO vo = new GoodsDetailVO();
        vo.setGoods(goodsVO);
        vo.setUser(user);
        vo.setRemainSeconds(remainSeconds);
        vo.setMiaoshaStatus(miaoshaStatus);
        return Result.success(vo);
    }

}
