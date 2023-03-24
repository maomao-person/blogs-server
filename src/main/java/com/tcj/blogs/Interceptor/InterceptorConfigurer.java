package com.tcj.blogs.Interceptor;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Component
@AllArgsConstructor
public class InterceptorConfigurer implements WebMvcConfigurer {

    private final PermissionInterceptor permissionInterceptor;
    private final SpecialInterceptor specialInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 拦截所有路径   多个拦截器组成一个拦截器链
        // 注册自定义两个拦截器
        // addPathPatterns  用来添加拦截规则，/** 表示拦截所有请求  /*/表示拦截目录
        // excludePatterns  用户排除拦截
        //拦截器的拦截顺序，是按照Web配置文件中注入拦截器的顺序执行的
//        registry.addInterceptor(specialInterceptor).addPathPatterns("/**");
        registry.addInterceptor(permissionInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/*", "/js/**", "/css/**", "/img/**")
                .excludePathPatterns("/user/login");

    }

}
