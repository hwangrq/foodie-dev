package com.yellowrq.controller;

import com.yellowrq.bo.AddressBO;
import com.yellowrq.pojo.UserAddress;
import com.yellowrq.service.AddressService;
import com.yellowrq.utils.JSONResult;
import com.yellowrq.utils.MobileEmailUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ClassName:AddressController
 * Package:com.yellowrq.controller
 * Description:
 *  地址接口controller
 * @author:yellowrq
 * @date: 2020/3/6 17:31
 */
@Api(value = "地址相关接口", tags = {"地址相关接口"})
@RestController
@RequestMapping("address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    /**
     * 用户在确认订单页面，可以针对收货地址做以下操作
     * 1.查询用户的所有收货地址列表
     * 2.新增收货地址
     * 3.删除收货地址
     * 4.修改收货地址
     * 5.设置默认地址
     */
    @ApiOperation(value = "查询用户的所有收货地址列表", notes = "查询用户的所有收货地址列表", httpMethod = "POST")
    @PostMapping("/list")
    public JSONResult list(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId){
        if (StringUtils.isBlank(userId)){
            return JSONResult.errorMsg("");
        }
        List<UserAddress> addressList = addressService.queryAll(userId);
        return JSONResult.ok(addressList);
    }

    @ApiOperation(value = "新增收货地址", notes = "新增收货地址", httpMethod = "POST")
    @PostMapping("/add")
    public JSONResult add(
            @ApiParam(name = "addressBO", value = "用户地址BO", required = true)
            @RequestParam AddressBO addressBO) {
        JSONResult checkRes = checkAddress(addressBO);
        if (checkRes.getStatus() != 200){
            return checkRes;
        }
        addressService.addNewUserAddress(addressBO);
        return JSONResult.ok();
    }

    private JSONResult checkAddress(@RequestParam @ApiParam(name = "addressBO", value = "用户地址BO") AddressBO addressBO) {
        String receiver = addressBO.getReceiver();
        if (StringUtils.isBlank(receiver)){
            return JSONResult.errorMsg("收货人不能为空");
        }
        if (receiver.length() > 12){
            return JSONResult.errorMsg("收货人姓名不能太长");
        }
        String mobile = addressBO.getMobile();
        if (StringUtils.isBlank(mobile)){
            return JSONResult.errorMsg("收货人手机号不能为空");
        }
        if (mobile.length() != 11){
            return JSONResult.errorMsg("收货人手机号长度不正确");
        }
        boolean isMobileOK = MobileEmailUtils.checkMobileIsOk(mobile);
        if (!isMobileOK){
            return JSONResult.errorMsg("收货人手机号格式有误");
        }
        String city = addressBO.getCity();
        String detail = addressBO.getDetail();
        String district = addressBO.getDistrict();
        String province = addressBO.getProvince();
        if (StringUtils.isBlank(province) ||
                StringUtils.isBlank(city) ||
                StringUtils.isBlank(detail) ||
                StringUtils.isBlank(district)){
            return JSONResult.errorMsg("收货地址信息不能为空");
        }
        return JSONResult.ok();
    }
}
