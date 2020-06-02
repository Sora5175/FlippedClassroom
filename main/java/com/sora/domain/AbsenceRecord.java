package com.sora.domain;

import lombok.Data;

import java.util.Date;

/**
 * @author Sora
 * @version 1.0
 * @date 2020/4/11 18:49
 */
@Data
public class AbsenceRecord {
    private Student student;
    private Course course;
    private Date time;
    private boolean export;
}
