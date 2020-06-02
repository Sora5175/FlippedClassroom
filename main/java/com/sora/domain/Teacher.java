package com.sora.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author Sora
 * @version 1.0
 * @date 2020/3/30 15:34
 */
@Data
public class Teacher extends User{
    private String workId;
    private School school;
    private String name;
    private String sex;
}