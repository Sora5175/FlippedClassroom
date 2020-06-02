package com.sora.dao;

import com.sora.domain.School;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Sora
 * @version 1.0
 * @date 2020/3/31 10:10
 */
@Repository
public interface ISchoolDao {
    public List<School> findAll();
}
