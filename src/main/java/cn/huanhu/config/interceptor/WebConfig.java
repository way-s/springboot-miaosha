package cn.huanhu.config.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author m
 * @className WebConfig
 * @description WebConfig
 * @date 2020/5/18
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    UserArgumentResolver userArgumentResolver;

    @Autowired
    AccessInterceptor accessInterceptor;

    /**
     *
     * @param argumentResolvers
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(userArgumentResolver);
    }

    /**
     * 登录拦截器
     * addInterceptor：需要一个实现HandlerInterceptor接口的拦截器实例
     * addPathPatterns：用于设置拦截器的过滤路径规则；addPathPatterns("/**")对所有请求都拦截
     * excludePathPatterns：用于设置不需要拦截的过滤规则
     * 拦截器主要用途：进行用户登录状态的拦截，日志的拦截等。
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        WebMvcConfigurer.super.addInterceptors(registry);
        //注册LoginHandlerInterceptor拦截器
        registry.addInterceptor(accessInterceptor);
    }

    /**
     * 添加Cors映射 解决跨域
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //设置允许跨域的路径
        registry.addMapping("/**")
                //设置允许跨域请求的域名
                .allowedOrigins("*")
                //是否允许证书
                .allowCredentials(true)
                //设置允许方法
                .allowedMethods("GET", "POST", "DELETE", "PUT","PATCH")
                //设置允许的header属性
                .allowedHeaders("*")
                //跨域允许时间
                .maxAge(3600);
    }
}
