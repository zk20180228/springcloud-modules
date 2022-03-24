package com.zk.skywalking.helloworld.main.beans;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: zk
 * @Date: 2022/1/24 11:48
 * @Description:
 * @Modified:
 * @version: V1.0
 * #  CREATE TABLE `user_info` (
 * #  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '用户id',
 * #  `username` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '用户名' COLLATE 'utf8mb4_general_ci',
 * #  `password` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '密码' COLLATE 'utf8mb4_general_ci',
 * #  `create_time` DATETIME NOT NULL COMMENT '创建时间',
 * #  PRIMARY KEY (`id`) USING BTREE
 * #  );
 *
 *
 */
@Data
@TableName("user_info")
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 8913402249872678706L;


    @TableId
    private int id;

    private String username;

    private String password;

    @TableField("create_time")
    private String createTime;
}
