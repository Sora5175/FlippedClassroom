package com.sora.service.Impl;

import com.sora.dao.IApplyDao;
import com.sora.dao.IUserDao;
import com.sora.domain.Apply;
import com.sora.domain.Student;
import com.sora.domain.Teacher;
import com.sora.service.IApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Sora
 * @version 1.0
 * @date 2020/4/16 21:10
 */
@Service
public class ApplyServiceImpl implements IApplyService {
    @Autowired
    private IApplyDao applyDao;

    @Autowired
    private IUserDao userDao;


    @Override
    public String addApply(long id) throws Exception {
        if(applyDao.findById(id)==null){
            applyDao.addApply(id);
            return "提交申请成功，等待审核";
        };
        return "已提交过申请，请等待审核";
    }

    @Override
    public List<Apply> findAll() throws Exception {
        return applyDao.findAll();
    }

    @Override
    public int upgrade(Apply apply) throws Exception {
        Student student = apply.getStudent();
        Teacher teacher = new Teacher();
        teacher.setId(student.getId());
        teacher.setSchool(apply.getSchool());
        teacher.setSex(student.getSex());
        teacher.setName(student.getName());
        teacher.setWorkId(student.getWorkId());
        applyDao.deleteApply(apply);
        userDao.deleteStudent(student);
        return userDao.addTeacher(teacher);
    }

    @Override
    public int deleteApply(Apply apply) throws Exception {
        return applyDao.deleteApply(apply);
    }
}
