package com.yellowrq.service.center;

import com.yellowrq.bo.center.CenterUserBO;
import com.yellowrq.pojo.Users;

/**
 * ClassName:CenterUserService
 * Package:com.yellowrq.service.center
 * Description:
 *  个人中心-用户服务
 * @author:yellowrq
 * @date: 2020/3/10 17:57
 */
public interface CenterUserService {

    /**
     * 根据用户id 查询用户信息
     * @param userId
     * @return
     */
    public Users queryUserInfo(String userId);

    /**
     * 更新用户信息
     * @param userId
     * @param centerUserBO
     */
    public Users updateUserInfo(String userId, CenterUserBO centerUserBO);
}
