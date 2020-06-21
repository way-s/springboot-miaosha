package cn.huanhu.controller;

import cn.huanhu.config.redis.prefix.UserKey;
import cn.huanhu.entity.MiaoshaUser;
import cn.huanhu.entity.User;
import cn.huanhu.service.MQSender;
import cn.huanhu.service.RedisService;
import cn.huanhu.service.UserService;
import cn.huanhu.utils.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author m
 * @className UserController
 * @description UserController
 * @date 2020/5/11
 */
@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    @Resource
    private UserService userService;

    @Resource
    private RedisService redisService;

    @Resource
    private MQSender sender;

    /**
     *
     * @return index.html
     */
    @RequestMapping("/")
    public String index(){
        return "index";
    }

    /**
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public  @ResponseBody
    Result<User> selectOne(Integer id) {
        User user=this.userService.queryById(id);
        return Result.success(user);
    }

    /**
     * 获取
     * @return
     */
    @RequestMapping(value = "redis/get")
    public @ResponseBody
    Result<User> redisGet(){
        User user = redisService.get(UserKey.getById,"8", User.class);
        logger.info(user.toString());
        return Result.success(user);
    }

    /**
     * 存放
     * @return
     */
    @GetMapping(value = "redis/set")
    @ResponseBody
    public Result<Boolean> redisSet(){
        User user = new User();
        user.setId(8);
        user.setName("iqoo");
        user.setEmail("WWW.iqoo.com");
        boolean key2 = redisService.set(UserKey.getById,"8",user);
        logger.info(user.toString());
        return Result.success(true);
    }

    /**
     * JMeter返回MiaoshaUser对象测试
     * @param model
     * @param user
     * @return
     */
    @RequestMapping("info")
    @ResponseBody
    public Result<MiaoshaUser> info(Model model,MiaoshaUser user){
        return Result.success(user);
    }

    @RequestMapping("mq")
    @ResponseBody
    public Result<String> mq(){
        sender.send("test mq");
        return Result.success("mq");
    }

    @RequestMapping("mq/fanout")
    @ResponseBody
    public Result<String> mqFanout(){
        sender.sendFanout("test mq");
        return Result.success("mq");
    }

    @RequestMapping("mq/header")
    @ResponseBody
    public Result<String> mqHeader(){
        sender.sendHeader("test,mq");
        return Result.success("mq");
    }
}