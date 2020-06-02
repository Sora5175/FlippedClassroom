package com.sora.dao;

import com.sora.domain.Course;
import com.sora.domain.Keyword;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Sora
 * @version 1.0
 * @date 2020/4/15 19:48
 */
@Repository
public interface IKeywordDao {
    public int addKeyword(String name, long courseId);

    public int deleteKeyword(Keyword keyword);

    public List<Keyword> findAllByCourseId(long courseId);

    public Keyword findByNameAndCourseId(String name,long courseId);

}
