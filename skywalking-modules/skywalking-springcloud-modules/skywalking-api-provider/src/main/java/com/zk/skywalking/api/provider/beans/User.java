package com.zk.skywalking.api.provider.beans;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author: zk
 * @Date: 2022/1/25 12:08
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
 */
@TableName("user_info")
@Data
public class User implements Serializable {


    private static final long serialVersionUID = -6902072069366072327L;

    @TableId(value="id",type = IdType.AUTO)
    private int id;

    @TableField("username")
    private String userName;

    @TableField("password")
    private String password;

    @TableField("create_time")
    private LocalDateTime createTime;
}
