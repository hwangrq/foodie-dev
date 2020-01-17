package com.yellowrq.service;

import com.yellowrq.bo.UserBO;
import com.yellowrq.pojo.Users;
import org.springframework.stereotype.Service;

/**
 * ClassName:UserService
 * Package:com.yellowrq.service
 * Description:
 *
 * @author:yellowrq
 * @date: 2020/1/15 11:36
 */
public interface UserService {

    /**
     * 判断用户名是否存在
     */
    public boolean queryUsernameIsExist(String username);

    /**
     * 创建用户
     */
    public Users createUser (UserBO userBO);
}
