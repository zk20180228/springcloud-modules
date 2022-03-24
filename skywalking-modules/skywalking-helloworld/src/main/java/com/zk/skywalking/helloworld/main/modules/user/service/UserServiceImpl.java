package com.zk.skywalking.helloworld.main.modules.user.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zk.skywalking.helloworld.main.beans.UserInfo;
import com.zk.skywalking.helloworld.main.serviceitfs.UserService;
import com.zk.skywalking.helloworld.main.modules.user.dao.UserMapper;
import org.springframework.stereotype.Service;

/**
 * @Author: zk
 * @Date: 2022/1/24 11:52
 * @Description:
 * @Modified:
 * @version: V1.0
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper,UserInfo> implements UserService {




    @Override
    public UserInfo findById(int id) {

        return this.getById(id);
    }
}
