package com.zk.skywalking.api.consumer.beans;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author: zk
 * @Date: 2022/1/25 12:08
 * @Description:
 * @Modified:
 * @version: V1.0

 */
@Data
public class User implements Serializable {


    private static final long serialVersionUID = 8674257720464848877L;

    private int id;

    private String userName;

    private String password;

    private LocalDateTime createTime;
}
