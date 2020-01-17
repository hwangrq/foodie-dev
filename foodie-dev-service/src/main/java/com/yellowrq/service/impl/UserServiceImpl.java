package com.yellowrq.service.impl;

import com.yellowrq.bo.UserBO;
import com.yellowrq.enums.Sex;
import com.yellowrq.mapper.UsersMapper;
import com.yellowrq.pojo.Users;
import com.yellowrq.service.UserService;
import com.yellowrq.utils.DateUtil;
import com.yellowrq.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.security.NoSuchAlgorithmException;
import java.util.Date;

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

    private static final String USER_FACE = "http://122.152.205.72:88/group1/M00/00/05/CpoxxFw_8_qAIlFXAAAcIhVPdSg994.png";

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean queryUsernameIsExist(String username) {
        Example userExample = new Example(Users.class);
        Example.Criteria userCriteria = userExample.createCriteria();
        userCriteria.andEqualTo("username", username);
        Users resultUser = usersMapper.selectOneByExample(userExample);
        return resultUser == null ? false :true;
    }

    @Override
    public Users createUser(UserBO userBO) {
        Users user = new Users();
        user.setUsername(userBO.getUsername());
        try {
            user.setPassword(MD5Util.getMD5Str(userBO.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //用户昵称默认为用户名
        user.setNickname(userBO.getUsername());
        //设置默认头像
        user.setFace(USER_FACE);
        //默认生日
        user.setBirthday(DateUtil.stringToDate("1900-01-01"));
        //默认性别 保密
        user.setSex(Sex.secret.type);
        user.setCreatedTime(new Date());
        user.setUpdatedTime(new Date());
        return null;
    }
}
