package com.yellowrq.conifg;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * ClassName:CorsConfig
 * Package:com.yellowrq.conifg
 * Description:
 *  跨域请求
 * @author: yellowrq
 * @date: 2020/2/24 14:20
 */
@Configuration
public class CorsConfig {

    public CorsConfig(){}

    @Bean
    public CorsFilter corsFilter(){
        // 1、添加cors配置信息
        CorsConfiguration config = new CorsConfiguration();
        //设置跨域的地址
        config.addAllowedOrigin("http://localhost:8080");
        //是否发送cookie信息 可以携带cookie 在跨域请求的时候获取同一个session
        config.setAllowCredentials(true);
        //设置允许请求的方式
        config.addAllowedMethod("*");
        //设置允许的跨域的请求头
        config.addAllowedHeader("*");

        // 2、为url添加映射路径
        UrlBasedCorsConfigurationSource corsSource = new UrlBasedCorsConfigurationSource();
        corsSource.registerCorsConfiguration("/**", config);

        // 3、返回重新定义好的corsSource
        return new CorsFilter(corsSource);
    }
}
