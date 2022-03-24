package com.zk.skywalking.helloworld.main.modules.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zk.skywalking.helloworld.main.beans.UserInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: zk
 * @Date: 2022/1/24 11:51
 * @Description:
 * @Modified:
 * @version: V1.0
 */
@Mapper
public interface UserMapper extends BaseMapper<UserInfo> {

}
