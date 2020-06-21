package cn.huanhu.config.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author m
 * @className LoginHandlerInterceptor
 * @description 登录拦截
 * @date 2020/6/8
 */

@Component
public class LoginHandlerInterceptor implements HandlerInterceptor {

    private static final Logger log= LoggerFactory.getLogger(LoginHandlerInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        HandlerMethod method = (HandlerMethod) handler;
//        log.info("--"+request.getRequestURI().toString());
//        MiaoshaUser user = (MiaoshaUser)request.getSession().getAttribute("user");
////        Object user = request.getSession();
//        if (user == null) {
//            logger.info("拦截"+"\t"+user);
////            response.sendRedirect("/");
//        }

        return true;
    }

    /**
     * 在请求处理之后、视图被渲染之前进行调用
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 在整个请求结束之后被调用
     * 主要用于资源清理工作
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
