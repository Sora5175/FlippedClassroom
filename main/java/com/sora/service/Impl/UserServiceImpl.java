package com.sora.service.Impl;

import com.sora.dao.IUserDao;
import com.sora.domain.Student;
import com.sora.domain.User;
import com.sora.service.IUserService;
import com.sora.utils.JWTUtils;
import com.sora.utils.WXUtils;
import com.sora.vo.TokenVO;
import com.sora.vo.WeiXinLoginMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserDao userDao;

    public User findUserByOpenId(String openId) {
        return userDao.findUserByOpenId(openId);
    }

    public TokenVO login(String token, String code,String avatarUrl) throws Exception{
        TokenVO tokenVO = new TokenVO();
        WeiXinLoginMessage weiXinLoginMessage = WXUtils.jscode2session(code);
        String openId = weiXinLoginMessage.getOpenid();
        User user = userDao.findUserByOpenId(openId);
        if(user != null){
            try{
                JWTUtils.verifyToken(token);
            }catch (Exception e){
                token = JWTUtils.createToken(user.getId(),user.getOpenId());
            }
            userDao.updateAvatarUrl(user.getId(),avatarUrl);
            user = findInfoById(user.getId());
            tokenVO.setToken(token);
            tokenVO.setUser(user);
        }
        return tokenVO;
    }

    public TokenVO registerStudent(Student student, String code,String avatarUrl) throws Exception{
        TokenVO tokenVO = new TokenVO();
        WeiXinLoginMessage weiXinLoginMessage = WXUtils.jscode2session(code);
        String openId = weiXinLoginMessage.getOpenid();
        student.setOpenId(openId);
        student.setRole(4);
        userDao.addUser(student);
        long id = student.getId();
        student.setId(id);
        userDao.addStudent(student);
        String token = JWTUtils.createToken(student.getId(),student.getOpenId());
        User user = userDao.findUserByOpenId(openId);
        tokenVO.setToken(token);
        tokenVO.setUser(user);
        return tokenVO;
    }

    public Student findStudentById(long id) throws Exception{
        return userDao.findStudentById(id);
    }

    public User findInfoById(long id){
        User user = userDao.findStudentById(id);
        if(user == null){
            user = userDao.findTeacherById(id);
        }
        if(user == null){
            user = userDao.findUserById(id);
        }
        return user;
    }
    public boolean isTeacher(long id)throws Exception{
        return (userDao.findTeacherById(id)!=null);
    }
}
