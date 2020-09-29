package com.yellowrq.controller;

import com.yellowrq.bo.ShopcartBO;
import com.yellowrq.utils.JSONResult;
import com.yellowrq.utils.JsonUtils;
import com.yellowrq.utils.RedisOperator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.ls.LSException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

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
public class ShopCartController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(ShopCartController.class);

    @Autowired
    private RedisOperator redisOperator;

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
        logger.info("shopcartBO:"+shopcartBO);
        // 前端用户在登录的情况下，添加商品到购物车，会同时在后端同步到购物车redis缓存
        // 需要判断当前购物车中是否包含已经存在的商品，若存在则累加购买数量
        String shopcartJson = redisOperator.get(FOODIE_SHOPCART + ":" + userId);
        List<ShopcartBO> list = null;
        if (StringUtils.isNotBlank(shopcartJson)) {
            list = JsonUtils.jsonToList(shopcartJson, ShopcartBO.class);
            //判断购物车中是否有此商品
            boolean isHaving = false;
            for (ShopcartBO sc : list) {
                String tmpSpecId = sc.getSpecId();
                if (tmpSpecId.equals(shopcartBO.getSpecId())) {
                    sc.setBuyCounts(sc.getBuyCounts() + shopcartBO.getBuyCounts());
                    isHaving = true;
                }
            }
            if (!isHaving) {
                list.add(shopcartBO);
            }
        } else {
            //redis中没有购物车 新购物车
            list = new ArrayList<>();
            list.add(shopcartBO);
        }
        //覆盖redis中原有购物车
        redisOperator.set(FOODIE_SHOPCART + ":" + userId, JsonUtils.objectToJson(list));
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
        // 用户在页面删除购物车中的商品数据，如果此时用户已登录，则需要同步删除后端redis购物车中的商品
        String shopcartJson = redisOperator.get(FOODIE_SHOPCART + ":" + userId);
        if (StringUtils.isNotBlank(shopcartJson)) {
            List<ShopcartBO> list = JsonUtils.jsonToList(shopcartJson, ShopcartBO.class);
            for (ShopcartBO shopcartBO : list) {
                if (itemSpecId.equals(shopcartBO.getSpecId())) {
                    list.remove(shopcartBO);
                    break;
                }
            }
            //覆盖现有redis购物车
            redisOperator.set(FOODIE_SHOPCART + ":" + userId, JsonUtils.objectToJson(list));
        }
        return JSONResult.ok();
    }
}
