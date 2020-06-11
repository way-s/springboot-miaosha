package cn.huanhu.config.interceptor;

import cn.huanhu.entity.MiaoshaUser;
import cn.huanhu.service.MiaoshaUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author m
 * @className UserArgumentResolver
 * @description UserArgumentResolver
 * @date 2020/5/18
 */
@Service
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    private static final Logger logger = LoggerFactory.getLogger(UserArgumentResolver.class);

    @Autowired
    private MiaoshaUserService userService;

    /**
     * 判断获取参数类型
     * @param methodParameter 方法参数
     * @return true ： false
     */
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        Class<?> clazz = methodParameter.getParameterType();
        return clazz == MiaoshaUser.class;
    }

    @Override
    public Object resolveArgument(MethodParameter Parameter, ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);

        String paramToken = request.getParameter(MiaoshaUserService.COOKIE_NAME_TOKEN);
        String cookieToken = getCookieValue(request,MiaoshaUserService.COOKIE_NAME_TOKEN);

        if(StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)){
//            logger.info("cookieToken:"+cookieToken+"\t"+"paramToken:"+paramToken);
            return null;
        }
        String token = StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
//        logger.info("token:"+token+"\n"+"cookieToken:"+cookieToken+"\t"+"paramToken:"+paramToken);
        return userService.getByToken(response,token);

    }

    /**
     *  获取cookie中的token值
     * @param request
     * @param cookieNameToken
     * @return string
     */
    private String getCookieValue(HttpServletRequest request, String cookieNameToken) {
        Cookie[] cookies = request.getCookies();
        if(cookies == null || cookies.length <= 0){
            logger.info("getCookieValue");
            return null;
        }
        for(Cookie cookie : cookies){
            if(cookie.getName().equals(cookieNameToken)){
                return cookie.getValue();
            }
        }
        return null;
    }
}
