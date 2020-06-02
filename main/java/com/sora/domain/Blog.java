package com.sora.domain;

import lombok.Data;

import java.security.Key;
import java.util.Date;
import java.util.List;

/**
 * @author Sora
 * @version 1.0
 * @date 2020/3/30 15:43
 */
@Data
public class Blog {
    private long id;
    private String description;
    private User user;
    private Date publishTime;
    private Source source;
    private Course course;
    private String forwardDescription;
    private List<Keyword> keywordList;
}