package com.yellowrq.service;

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
}
