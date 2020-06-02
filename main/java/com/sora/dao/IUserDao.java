package com.sora.dao;

import com.sora.domain.Student;
import com.sora.domain.Teacher;
import com.sora.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author sora
 * @version 1.0
 * @date 2020/3/26 9:52
 */
@Repository
public interface IUserDao {
    public User findUserByOpenId(String openId);

    public int addUser(User user);

    public int addStudent(Student student);

    public int deleteStudent(Student student);

    public int addTeacher(Teacher teacher);

    public int updateAvatarUrl(long id,String avatarUrl);

    public Student findStudentById(long id);

    public Teacher findTeacherById(long id);

    public User findUserById(long id);
}

