package com.yellowrq.service.impl;

import com.yellowrq.mapper.UsersMapper;
import com.yellowrq.pojo.Users;
import com.yellowrq.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

/**
 * ClassName:UserServiceImpl
 * Package:com.yellowrq.service.impl
 * Description:
 *
 * @author:yellowrq
 * @date: 2020/1/15 11:38
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    public UsersMapper usersMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean queryUsernameIsExist(String username) {
        Example userExample = new Example(Users.class);
        Example.Criteria userCriteria = userExample.createCriteria();
        userCriteria.andEqualTo("username", username);
        Users resultUser = usersMapper.selectOneByExample(userExample);
        return resultUser == null ? false :true;
    }
}
