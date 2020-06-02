package com.sora.service;

import com.sora.domain.Course;
import com.sora.domain.Question;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Sora
 * @version 1.0
 * @date 2020/4/8 15:50
 */
public interface IQuestionService {
    public List<Question> findAllByBlogId(long blogId) throws Exception;

    public List<Question> findAllByCourseId(long courseId) throws Exception;

    public List<Question> findAllByCourseIdAndKey(long courseId,String key) throws Exception;

    public int deleteQuestion(long userId,Question question) throws Exception;

    public int importQuestions(long courseId, MultipartFile file) throws Exception;
}
