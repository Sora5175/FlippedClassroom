package com.sora.service;

import com.sora.domain.Course;

import java.util.List;

/**
 * @author Sora
 * @version 1.0
 * @date 2020/4/2 21:15
 */
public interface ICourseService {
    public int addCourse(long id,String name) throws Exception;

    public List<Course> findAllByUserId(long userId) throws Exception;

    public Course findByCourseId(long courseId) throws Exception;

    public int joinCourse(long userId,long courseId) throws Exception;

    public int deleteCourse(long userId,Course course) throws Exception;
}
