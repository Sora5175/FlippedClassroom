package com.sora.service;

import com.sora.domain.Apply;

import java.util.List;

/**
 * @author Sora
 * @version 1.0
 * @date 2020/4/16 21:08
 */
public interface IApplyService {
    public String addApply(long id) throws Exception;

    public List<Apply> findAll() throws Exception;

    public int upgrade(Apply apply) throws Exception;

    public int deleteApply(Apply apply) throws Exception;
}
