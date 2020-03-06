package com.yellowrq.controller;

import com.yellowrq.bo.ShopcartBO;
import com.yellowrq.utils.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ClassName:ShopCartController
 * Package:com.yellowrq.controller
 * Description:
 *  购物车
 * @author:yellowrq
 * @date: 2020/3/6 10:30
 */
@Api(value = "购物车接口controller", tags = {"购物车接口相关API"})
@RestController
@RequestMapping("shopcart")
public class ShopCartController {

    static final Logger logger = LoggerFactory.getLogger(ShopCartController.class);

    @ApiOperation(value = "加入购物车", notes = "向购物车内添加商品", httpMethod = "POST")
    @PostMapping("/add")
    public JSONResult add(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId,
            @ApiParam(name = "shopcartBO", value = "购物车业务对象", required = true)
            @RequestBody ShopcartBO shopcartBO,
            HttpServletRequest request,
            HttpServletResponse response) {
        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("");
        }
        System.out.println(shopcartBO);
        // 前端用户在登录的情况下，添加商品到购物车，会同时在后端同步到购物车redis缓存
        return JSONResult.ok();
    }


    @ApiOperation(value = "从购物车中删除商品", notes = "从购物车中删除商品", httpMethod = "POST")
    @PostMapping("/del")
    public JSONResult del(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId,
            @ApiParam(name = "itemSpecId", value = "商品规格id")
            @RequestParam String itemSpecId
            ){
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(itemSpecId)){
            return JSONResult.errorMsg("参数不能为空");
        }
        // 用户在页面删除购物车中的商品数据，如果此时用户已登录，则需要同步删除后端购物车中的商品
        return JSONResult.ok();
    }
}
