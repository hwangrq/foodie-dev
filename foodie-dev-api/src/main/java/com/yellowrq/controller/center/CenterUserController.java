package com.yellowrq.controller.center;

import com.yellowrq.bo.center.CenterUserBO;
import com.yellowrq.controller.BaseController;
import com.yellowrq.pojo.Users;
import com.yellowrq.service.center.CenterUserService;
import com.yellowrq.utils.CookieUtils;
import com.yellowrq.utils.JSONResult;
import com.yellowrq.utils.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ClassName:CenterUserController
 * Package:com.yellowrq.controller.center
 * Description:
 *
 * @author:yellowrq
 * @date: 2020/3/10 17:54
 */

@Api(value = "用户信息接口", tags = {"用户信息相关接口"})
@RestController
@RequestMapping("userInfo")
public class CenterUserController extends BaseController {

    @Autowired
    private CenterUserService centerUserService;

    @ApiOperation(value = "修改用户信息", notes = "修改用户信息", httpMethod = "POST")
    @PostMapping("update")
    public JSONResult update(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId,
            @ApiParam(name = "centerUserBO", value = "个人中心用户业务对象", required = true)
            @RequestBody CenterUserBO centerUserBO,
            HttpServletRequest request,
            HttpServletResponse response) {
        Users userResult = centerUserService.updateUserInfo(userId, centerUserBO);
        userResult = setNullProperty(userResult);
        CookieUtils.setCookie(request, response, "user",
                JsonUtils.objectToJson(userResult), true);
        //to后续要改,增加令牌token，会整合进redis，分布式会话
        return JSONResult.ok();
    }

    private Users setNullProperty(Users userResult){
        userResult.setCreatedTime(null);
        userResult.setRealname(null);
        userResult.setUpdatedTime(null);
        userResult.setBirthday(null);
        userResult.setPassword(null);
        userResult.setMobile(null);
        userResult.setEmail(null);
        return userResult;
    }
}
