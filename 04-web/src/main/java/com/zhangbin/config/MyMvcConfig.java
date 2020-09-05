package com.zhangbin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 认认真真敲代码，开开心心每一天
 *
 * @Date 2020/9/4-16:21
 */
//扩展springmvc
@Configuration//@Configuration注解表示这是一个配置类，可以拓展其他属性
public class MyMvcConfig implements WebMvcConfigurer {

//    视图跳转
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/index.html").setViewName("index");
    }
}
