package com.sora.service.Impl;

import com.sora.dao.ICourseDao;
import com.sora.domain.Course;
import com.sora.domain.Teacher;
import com.sora.service.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Sora
 * @version 1.0
 * @date 2020/4/2 21:16
 */
@Service
public class CourseServiceImpl implements ICourseService {

    @Autowired
    private ICourseDao courseDao;

    public int addCourse(long id, String name) throws Exception {
        Teacher teacher = new Teacher();
        teacher.setId(id);
        Course course = new Course();
        course.setTeacher(teacher);
        course.setName(name);
        course.setStatus(1);
        return courseDao.addCourse(course);
    }

    public List<Course> findAllByUserId(long userId) throws Exception {
        return courseDao.findAllByUserId(userId);
    }

    public Course findByCourseId(long courseId)throws Exception{
        return courseDao.findByCourseId(courseId);
    }

    public int joinCourse(long userId,long courseId) throws Exception{
        return courseDao.joinCourse(userId,courseId);
    }

    public int deleteCourse(long userId,Course course) throws Exception{
        if(userId == courseDao.findUser(course).getId()){
            return courseDao.deleteCourse(course);
        }
        return 0;
    }
}
