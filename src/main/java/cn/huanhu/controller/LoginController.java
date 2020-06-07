package cn.huanhu.controller;

import cn.huanhu.config.exception.GlobalException;
import cn.huanhu.entity.vo.LoginVo;
import cn.huanhu.service.MiaoshaUserService;
import cn.huanhu.utils.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "登录接口")
@Controller
@RequestMapping(value = "login/")
public class LoginController {

    private static final Logger logger= LoggerFactory.getLogger(LoginController.class);

    @Resource
    private MiaoshaUserService miaoshaUserService;

    /**
     *
     * @return login.html登录页
     */
    @ApiOperation(httpMethod = "POST",value = "login.html")
    @RequestMapping("toLogin")
    public String toLoginPage(){
        return "login";
    }

    /**
     * 注册
     * @param id 手机号
     * @param password 密码
     * @param nickname 昵称
     * @return
     * @throws GlobalException
     */
    @ApiOperation(httpMethod = "POST",value = "注册")
    @ApiImplicitParams ({
            @ApiImplicitParam( name = "id",value = "手机号",required = true ,dataType = "String"),
            @ApiImplicitParam( name = "password",value = "密码",required = true ,dataType = "String"),
            @ApiImplicitParam( name = "nickname",value = "昵称",required = true ,dataType = "String")
    })
    @RequestMapping(value = "doRegister")
    @ResponseBody
    public Result<Object> doRegister(@RequestParam String id,@RequestParam String password,@RequestParam String nickname) throws GlobalException {
        logger.info(password+"\t"+id+"\t"+nickname);
        //注册
        Object result = miaoshaUserService.doRegister(id,password,nickname);
        return Result.success(result);
    }

    /**
     * 密码登录
     * @param response
     * @param loginVo
     * @return
     * @throws GlobalException
     */
    @ApiOperation(httpMethod = "POST",value = "密码登录")
    @ApiImplicitParams ({
            @ApiImplicitParam( name = "mobile",value = "手机号",required = true ,dataType = "String"),
            @ApiImplicitParam( name = "password",value = "密码",required = true ,dataType = "String")
    })
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
    @ApiOperation(httpMethod = "POST",value = "发送验证码")
    @ApiImplicitParam( name = "mobile",value = "手机号",required = true ,dataType = "String")
    @RequestMapping(value = "doSendC")
    @ResponseBody
    public Result<Boolean> sendPhoneMsg(@RequestParam(value = "mobile") String mobile) throws GlobalException {
//        发送验证码
        Boolean code = miaoshaUserService.doSendVerCode(mobile);
        return Result.success(code);
    }

    /**
     * 校验验证码
     * @param loginVo 手机号 验证码
     */
    @ApiOperation(httpMethod = "POST",value = "校验验证码")
    @ApiImplicitParams ({
            @ApiImplicitParam( name = "mobile",value = "手机号",required = true ,dataType = "String"),
            @ApiImplicitParam( name = "password",value = "密码",required = true ,dataType = "String")
    })
    @RequestMapping(value = "do/check")
    @ResponseBody
    public Result<String> checkCode(HttpServletResponse response, @Valid LoginVo loginVo) throws GlobalException {
//        登录
        String token = miaoshaUserService.doPhoneL(response,loginVo);
        return Result.success(token);
    }
}
