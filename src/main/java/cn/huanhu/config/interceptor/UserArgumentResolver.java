package cn.huanhu.config.interceptor;

import cn.huanhu.config.access.UserContext;
import cn.huanhu.entity.MiaoshaUser;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @author m
 * @className UserArgumentResolver
 * @description UserArgumentResolver
 * @date 2020/5/18
 */
@Service
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    /**
     * 判断获取参数类型
     *
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
        return UserContext.getUser();

    }


}
