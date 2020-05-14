package cn.huanhu.controller;

import cn.huanhu.entity.vo.LoginVo;
import cn.huanhu.service.impl.MiaoshaUserService;
import cn.huanhu.utils.ValidatorUtil;
import cn.huanhu.utils.result.CodeMsg;
import cn.huanhu.utils.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;

/**
 * @author m
 * @className LoginController
 * @description LoginController
 * @date 2020/5/13
 */
@Controller
@RequestMapping(value = "login/")
public class LoginController {

    private static final Logger logger= LoggerFactory.getLogger(LoginController.class);

    @Resource
    private MiaoshaUserService miaoshaUserService;

    @GetMapping("toLogin")
    public String toLoginPage(){
        return "login";
    }

    @GetMapping("doLogin")
    public Result<Boolean> doLogin(LoginVo loginVo){
        logger.info(loginVo.toString());
        //参数校验
        String mobile = loginVo.getMobile();
        String passInput = loginVo.getPassword();
        if(StringUtils.isEmpty(passInput)){
            return Result.error(CodeMsg.PASSWORD_EMPTY);
        }
        if(StringUtils.isEmpty(mobile)){
            return Result.error(CodeMsg.MOBILE_EMPTY);
        }
        if(!ValidatorUtil.isMobile(mobile)){
            return Result.error(CodeMsg.MOBILE_ERROR);
        }
        CodeMsg cm = miaoshaUserService.login(loginVo);
        if(cm.getCode() == 0){
            return Result.success(true);
        }else {
            return Result.error(cm);
        }
    }


}
