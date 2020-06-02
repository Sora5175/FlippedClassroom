package com.sora.service;

import com.sora.domain.Student;
import com.sora.domain.Teacher;
import com.sora.domain.User;
import com.sora.vo.ResultVO;
import com.sora.vo.TokenVO;

import java.io.IOException;

public interface IUserService {
    public User findUserByOpenId(String openId);

    public TokenVO login(String token, String code,String avatarUrl) throws Exception;

    public TokenVO registerStudent(Student student, String code,String avatarUrl) throws Exception;

    public Student findStudentById(long id) throws Exception;

    public User findInfoById(long id) throws Exception;

    public boolean isTeacher(long id)throws Exception;
}
