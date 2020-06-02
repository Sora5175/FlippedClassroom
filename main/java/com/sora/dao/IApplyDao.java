package com.sora.dao;

import com.sora.domain.Apply;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Sora
 * @version 1.0
 * @date 2020/4/16 20:46
 */
@Repository
public interface IApplyDao {
    public List<Apply> findAll();

    public Apply findById(long id);

    public int deleteApply(Apply apply);

    public int addApply(long id);
}