package com.yellowrq.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;


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

    static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @GetMapping("/hello")
    public Object hello() {
        logger.debug("debug: hello");
        logger.info("info: hello");
        logger.warn("warn: hello");
        logger.error("error: hello");
        return "Hello World";
    }
}
