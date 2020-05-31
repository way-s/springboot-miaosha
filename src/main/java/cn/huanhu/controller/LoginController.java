package cn.huanhu.controller;

import cn.huanhu.config.exception.GlobalException;
import cn.huanhu.entity.vo.LoginVo;
import cn.huanhu.service.MiaoshaUserService;
import cn.huanhu.utils.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @author m
 * @className LoginController
 * @description LoginController
 * @date 2020/5/13
 */
@CrossOrigin
@Controller
@RequestMapping(value = "login/")
public class LoginController {

    private static final Logger logger= LoggerFactory.getLogger(LoginController.class);

    @Resource
    private MiaoshaUserService miaoshaUserService;

    @RequestMapping("toLogin")
    public String toLoginPage(){
        return "login";
    }

    /**
     * 注册
     * @param id
     * @param password
     * @param nickname
     * @return
     * @throws GlobalException
     */
    @RequestMapping(value = "doRegister")
    @ResponseBody
    public Result<String> doRegister(@RequestParam String id,@RequestParam String password,@RequestParam String nickname) throws GlobalException {
        logger.info(password+"\t"+id+"\t"+nickname);
        //注册
        String result = miaoshaUserService.doRegister(id,password,nickname);
        return Result.success(result);
    }

    /**
     * 密码登录
     * @param response
     * @param loginVo
     * @return
     * @throws GlobalException
     */
    @RequestMapping(value = "doLogin")
    @ResponseBody
    public Result<String> doLogin(HttpServletResponse response, @Valid LoginVo loginVo) throws GlobalException {
        logger.info(loginVo.toString());
        //登录
        String token = miaoshaUserService.doLogin(response,loginVo);
        return Result.success(token);
    }


    /**
     * 发送验证码
     * @param mobile 手机号
     */
    @RequestMapping(value = "doSendC")
    @ResponseBody
    public Result<String> sendPhoneMsg(@RequestParam(value = "mobile") String mobile) throws GlobalException {
//        登录
        String code = miaoshaUserService.doSendVerCode(mobile);
        return Result.success(code);
    }

    /**
     * 校验验证码
     * @param loginVo 手机号 验证码
     */
    @RequestMapping(value = "do/check")
    @ResponseBody
    public Result<String> checkCode(HttpServletResponse response, @Valid LoginVo loginVo) throws GlobalException {
//        登录
        String token = miaoshaUserService.doPhoneL(response,loginVo);
        return Result.success(token);
    }
}
