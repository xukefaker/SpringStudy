package com.faker.day01.MvcConfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyMvcController implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/index.html").setViewName("index");
        registry.addViewController("/404").setViewName("404");
        registry.addViewController("/dashboard").setViewName("dashboard");
        registry.addViewController("/list").setViewName("list");
        registry.addViewController("/reg").setViewName("reg");
        registry.addViewController("/getpasswd").setViewName("getpasswd");
        registry.addViewController("/user/index.html").setViewName("index");

    }
}
