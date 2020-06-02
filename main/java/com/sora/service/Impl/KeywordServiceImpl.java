package com.sora.service.Impl;

import com.sora.dao.IKeywordDao;
import com.sora.domain.Course;
import com.sora.domain.Keyword;
import com.sora.service.IKeywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Sora
 * @version 1.0
 * @date 2020/4/15 19:54
 */
@Service
public class KeywordServiceImpl implements IKeywordService {

    @Autowired
    private IKeywordDao keywordDao;

    @Override
    public int addKeyword(String name, long courseId) throws Exception {
        return keywordDao.addKeyword(name,courseId);
    }

    @Override
    public int deleteKeyword(Keyword keyword) throws Exception {
        return keywordDao.deleteKeyword(keyword);
    }

    @Override
    public List<Keyword> findAllByCourseId(long courseId) throws Exception {
        return keywordDao.findAllByCourseId(courseId);
    }

}
