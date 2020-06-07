package cn.huanhu.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author m
 * @className SwaggerConfig
 * @description SwaggerConfig
 * @date 2020/6/1
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //为当前包路径
                .apis(RequestHandlerSelectors.basePackage("cn.huanhu.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 构建API文档的详细信息函数
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //页面标题
                .title("miaosha项目RESTful APIs")
                //创建人
                .contact(new Contact("huanhu","127.0.0.1:8000","null"))
                //描述
                .description("miaosha项目后台api接口文档")
                //版本号
                .version("1.0")
                .build();
    }
}
