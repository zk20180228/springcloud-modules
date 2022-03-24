package com.zk.skywalking.helloworld.main.serviceitfs;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zk.skywalking.helloworld.main.beans.UserInfo;

/**
 * @Author: zk
 * @Date: 2022/1/24 11:53
 * @Description:
 * @Modified:
 * @version: V1.0
 */
public interface UserService extends IService<UserInfo> {


    UserInfo findById(int id);
}
