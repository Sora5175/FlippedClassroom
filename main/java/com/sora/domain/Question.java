package com.sora.domain;

import lombok.Data;

import java.util.List;

/**
 * @author Sora
 * @version 1.0
 * @date 2020/3/30 16:01
 */
@Data
public class Question {
    private long id;
    private String content;
    private List<Option> optionList;
    private String answerDescription;
    private List<Keyword> keywordList;
}