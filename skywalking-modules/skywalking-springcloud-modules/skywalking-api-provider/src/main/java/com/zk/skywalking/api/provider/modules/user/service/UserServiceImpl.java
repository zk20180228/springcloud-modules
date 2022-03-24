package com.zk.skywalking.api.provider.modules.user.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zk.skywalking.api.provider.beans.User;
import com.zk.skywalking.api.provider.modules.user.dao.UserMapper;
import com.zk.skywalking.api.provider.serviceitfs.UserService;
import org.springframework.stereotype.Service;

/**
 * @Author: zk
 * @Date: 2022/1/25 12:09
 * @Description:
 * @Modified:
 * @version: V1.0
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
