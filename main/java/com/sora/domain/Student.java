package com.sora.domain;

import lombok.Data;

/**
 * @author Sora
 * @version 1.0
 * @date 2020/3/30 15:35
 */
@Data
public class Student extends User{
    private String workId;
    private Class aClass;
    private String name;
    private String sex;
}
