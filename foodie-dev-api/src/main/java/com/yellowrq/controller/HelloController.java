package com.yellowrq.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * ClassName:HelloController
 * Package:com.yellowrq.controller
 * Description:
 *
 * @author:yellowrq
 * @date: 2020/1/13 14:54
 */
@ApiIgnore
@RestController
public class HelloController {

    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);


    @GetMapping("/hello")
    public Object hello() {
        logger.debug("debug: hello");
        logger.info("info: hello");
        logger.warn("warn: hello");
        logger.error("error: hello");
        return "Hello World";
    }

    @GetMapping("/setSession")
    public Object setSession(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.setAttribute("userInfo", "new user");
        session.setMaxInactiveInterval(3600);
        session.getAttribute("userInfo");
//        session.removeAttribute("userInfo");
        return "ok";
    }


}
