package com.sora.dao;

import com.sora.domain.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Sora
 * @version 1.0
 * @date 2020/4/8 15:35
 */
@Repository
public interface IQuestionDao {
    public List<Question> findAllByBlogId(long blogId);

    public List<Question> findAllByCourseId(long courseId);

    public List<Question> findAllByCourseIdAndKey(long courseId,String key);

    public int deleteQuestion(Question question);

    public User findUser(Question question);

    public int addQuestion(Question question, long courseId);

    public int addOption(Option option,Question question);

    public int addKeyword(Keyword keyword,Question question);
}