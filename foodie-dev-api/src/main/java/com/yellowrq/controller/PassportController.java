package com.yellowrq.controller;

import com.yellowrq.service.UserService;
import com.yellowrq.utils.JSONResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName:PassportController
 * Package:com.yellowrq.controller
 * Description:
 *
 * @author:yellowrq
 * @date: 2020/1/15 11:53
 */
@RestController
@RequestMapping("passport")
public class PassportController {

    @Autowired
    public UserService userService;

    @GetMapping("/usernameIsExist")
    public JSONResult usernameIsExist(@RequestParam String username){
        //1.判断用户名不为空
        if (StringUtils.isBlank(username)){
            return JSONResult.errorMsg("用户名不能为空");
        }
        //2.查找用户名是否存在
        boolean isExist = userService.queryUsernameIsExist(username);
        if (isExist){
            return JSONResult.errorMsg("用户名已经存在");
        }
        //3.请求成功，用户名没有重复
        return JSONResult.ok();
    }
}
