package com.sora.domain;

import lombok.Data;

/**
 * @author Sora
 * @version 1.0
 * @date 2020/4/8 15:36
 */
@Data
public class Option {
    private long id;
    private String content;
    private boolean correct;
}
