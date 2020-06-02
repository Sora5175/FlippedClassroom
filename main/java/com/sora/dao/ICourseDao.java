package com.sora.dao;

import com.sora.domain.Course;
import com.sora.domain.Student;
import com.sora.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Sora
 * @version 1.0
 * @date 2020/4/2 21:21
 */
@Repository
public interface ICourseDao {
    public int addCourse(Course course);

    public List<Course> findAllByUserId(long userId);

    public Course findByCourseId(long courseId);

    public int joinCourse(long userId,long courseId);

    public int deleteCourse(Course course);

    public User findUser(Course course);
}
