package com.yellowrq.service;

import com.yellowrq.bo.AddressBO;
import com.yellowrq.pojo.UserAddress;

import java.util.List;

/**
 * ClassName:AddressService
 * Package:com.yellowrq.service
 * Description:
 *
 * @author:yellowrq
 * @date: 2020/3/6 17:40
 */
public interface AddressService {

    /**
     * 根据用户id查询用户的地址列表
     * @param userId
     * @return
     */
    public List<UserAddress> queryAll(String userId);

    /**
     * 用户新增地址
     * @param addressBO
     */
    public void addNewUserAddress(AddressBO addressBO);
}
