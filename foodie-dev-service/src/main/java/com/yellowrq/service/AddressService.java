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

    /**
     * 用户修改地址
     * @param addressBO
     */
    public void updateUserAddress(AddressBO addressBO);

    /**
     * 用户删除地址
     * @param userId
     * @param addressId
     */
    public void deleteUserAddress(String userId, String addressId) ;

    /**
     * 修改设置默认地址
     * @param userId
     * @param addressId
     */
    public void updateUserAddressToBeDefault(String userId, String addressId);

    /**
     * 根据用户id和地址id 查询具体的用户地址对象信息
     * @param userId
     * @param addressId
     * @return
     */
    public UserAddress queryUserAddress(String userId, String addressId);
}
