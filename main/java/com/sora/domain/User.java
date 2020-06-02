package com.sora.domain;

import lombok.Data;

/**
 * @author sora
 * @version 1.0
 * @date 2020/3/26 9:52
 */

@Data
public class User {
    /**
     * role 取值：
     *          1.超级管理员
     *          2.管理员
     *          3教师
     *          4.学生
     *          默认值：4
     */
    private long id;
    private String openId;
    private int role;
    private String avatarUrl;
}
