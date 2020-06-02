package com.sora.service;

import com.sora.domain.Course;
import com.sora.domain.Keyword;

import java.util.List;

/**
 * @author Sora
 * @version 1.0
 * @date 2020/4/15 19:53
 */
public interface IKeywordService {
    public int addKeyword(String name, long courseId) throws Exception;

    public int deleteKeyword(Keyword keyword) throws Exception;

    public List<Keyword> findAllByCourseId(long courseId) throws Exception;
}
