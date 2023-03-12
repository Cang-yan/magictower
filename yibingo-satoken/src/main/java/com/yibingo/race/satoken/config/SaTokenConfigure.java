package com.yibingo.race.satoken.config;

import cn.dev33.satoken.interceptor.SaAnnotationInterceptor;
import com.yibingo.race.pay.interceptor.AliPayInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc：
 * @author： xhs
 * @date： 2021/8/19 13:26
 * @version： JDK 1.8
 */
@Configuration
public class SaTokenConfigure implements WebMvcConfigurer {

//    /**
//     * 注册拦截器
//     *
//     * @param registry
//     */
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        List<String> urls = new ArrayList<>();
//        urls.add("/favicon.ico");
//        urls.add("/error");
//        urls.add("/swagger-resources/**");
//        urls.add("/webjars/**");
//        urls.add("/v2/**");
//        urls.add("/doc.html");
//        urls.add("**/swagger-ui.html");
//        urls.add("/swagger-ui.html/**");
//
//        // 排除注册登录接口不需要鉴权
//        urls.add("/register");
//        urls.add("/login/user");
//
//
//        // 注册Sa-Token的路由拦截器，并排除登录接口或其他可匿名访问的接口地址 (与注解拦截器无关)
//        registry.addInterceptor(new SaRouteInterceptor())
//                .addPathPatterns("/**")
//
//                .excludePathPatterns(urls);
//
//    }

    /**
     * 注册Sa-Token的注解拦截器，打开注解式鉴权功能
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        List<String> urls = new ArrayList<>();
        urls.add("/favicon.ico");
        urls.add("/error");
        urls.add("/swagger-resources/**");
        urls.add("/webjars/**");
        urls.add("/v2/**");
        urls.add("/doc.html");
        urls.add("**/swagger-ui.html");
        urls.add("/swagger-ui.html/**");
        urls.add("/static/**");
        // 排除注册登录接口不需要鉴权

        urls.add("/login/**");

        /*registry.addInterceptor(new AliPayInterceptor())
                .addPathPatterns("alipay/app")
                .addPathPatterns("alipay/h5")
                .excludePathPatterns("/core/**");
*/
        // 注册注解拦截器，并排除不需要注解鉴权的接口地址 (与登录拦截器无关)
        registry.addInterceptor(new SaAnnotationInterceptor()).addPathPatterns("/**")
                // 排除注册登录接口不需要鉴权
                .excludePathPatterns(urls);


    }

    // 资源映射增加
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("doc.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");

        WebMvcConfigurer.super.addResourceHandlers(registry);
    }


    // <-cors
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedOrigins("*")
                .allowedHeaders("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .maxAge(3600);
    }


}
