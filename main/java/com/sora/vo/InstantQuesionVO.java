package com.sora.vo;

import com.sora.domain.User;
import lombok.Data;

import java.util.Date;

/**
 * @author Sora
 * @version 1.0
 * @date 2020/4/11 17:04
 */
@Data
public class InstantQuesionVO {
    private long courseId;
    private int optionNumber;
    private User user;
    private int second;
    private boolean muti;
    private boolean again;
    private boolean end;
    private Date publishTime;
    private String answer;
}