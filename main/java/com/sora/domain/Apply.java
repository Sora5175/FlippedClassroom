package com.sora.domain;

import lombok.Data;

/**
 * @author Sora
 * @version 1.0
 * @date 2020/4/16 20:46
 */
@Data
public class Apply {
    private long id;
    private Student student;
    private School school;
}
