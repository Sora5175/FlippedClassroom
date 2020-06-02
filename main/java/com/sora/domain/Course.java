package com.sora.domain;

import lombok.Data;

import java.util.List;

/**
 * @author Sora
 * @version 1.0
 * @date 2020/4/2 21:21
 */
@Data
public class Course {
    private long id;
    private String name;
    private Teacher teacher;
    private List<Student> studentList;
    private List<Keyword> keywordList;
    private int status;
}
