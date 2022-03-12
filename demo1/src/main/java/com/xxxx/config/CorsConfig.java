package com.xxxx.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //设置允许跨域的路径
        registry.addMapping("/**")
                //允许跨域请求的域名
                .allowedOrigins("*")
                //是否允许cookie
                .allowCredentials(true)
                //允许请求的方式
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                //允许设置header属性
                .allowedHeaders("*")
                //跨越与允许时间
                .maxAge(3600);
    }
}
