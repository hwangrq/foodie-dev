package com.yellowrq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * ClassName:Application
 * Package:com.yellowrq
 * Description:
 *  springboot
 * @author:yellowrq
 * @date: 2020/1/13 14:48
 */
@SpringBootApplication
// 扫描 mybatis 通用 mapper 所在的包
@MapperScan(basePackages = "com.yellowrq.mapper")
// 扫描所有包以及相关组件包
@ComponentScan(basePackages = {"com.yellowrq", "org.n3r.idworker"})
//@EnableTransactionManagement
@EnableScheduling       // 开启定时任务
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
