package com.sora.domain;

import lombok.Data;

import java.util.Date;

/**
 * @author Sora
 * @version 1.0
 * @date 2020/4/10 10:42
 */
@Data
public class Comment {
    private long id;
    private String content;
    private User user;
    private boolean top;
    private long preId;
    private Date publishTime;
}
