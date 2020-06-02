package com.sora.service.Impl;

import com.sora.dao.ISchoolDao;
import com.sora.domain.School;
import com.sora.service.ISchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Sora
 * @version 1.0
 * @date 2020/3/31 10:38
 */
@Service
public class SchoolServiceImpl implements ISchoolService {

    @Autowired
    private ISchoolDao schoolDao;

    public List<School> findAll() {
        return schoolDao.findAll();
    }
}
