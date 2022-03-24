package com.zk.skywalking.api.provider.modules.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zk.skywalking.api.provider.beans.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: zk
 * @Date: 2022/1/25 12:09
 * @Description:
 * @Modified:
 * @version: V1.0
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
