package com.yellowrq.conifg;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * ClassName:WebMvcConfig
 * Package:com.yellowrq.conifg
 * Description:
 *
 * @author:yellowrq
 * @date: 2020/3/10 13:17
 */
@Configuration
public class WebMvcConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {

        return builder.build();
    }
}
