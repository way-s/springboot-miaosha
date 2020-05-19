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

    @RequestMapping(value = "doLogin")
    @ResponseBody
    public Result<String> doLogin(HttpServletResponse response, @Valid LoginVo loginVo) throws GlobalException {
        logger.info(loginVo.toString());
        //登录
        String token = miaoshaUserService.doLogin(response,loginVo);
        return Result.success(token);
    }


}
