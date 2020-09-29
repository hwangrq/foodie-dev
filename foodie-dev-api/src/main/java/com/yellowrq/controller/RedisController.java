package com.yellowrq.controller;

import com.yellowrq.utils.RedisOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * ClassName:RedisController
 * Package:com.yellowrq.controller
 * Description:
 *  RedisController
 * @author:yellowrq
 * @date: 2020/9/22 14:29
 */
@ApiIgnore
@RestController
@RequestMapping("redis")
public class RedisController {

    @Autowired
    private RedisOperator redisOperator;


    @GetMapping("/set")
    public Object set(String key, String value){
        redisOperator.set(key, value);
        return "OK";
    }

    @GetMapping("/get")
    public String get(String key) {
        return redisOperator.get(key);
    }

    @GetMapping("delete")
    public String delete(String key) {
        redisOperator.del(key);
        return "ok";
    }
}
