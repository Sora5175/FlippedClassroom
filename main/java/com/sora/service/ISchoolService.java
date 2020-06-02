package com.sora.service;

import com.sora.domain.School;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Sora
 * @version 1.0
 * @date 2020/3/31 10:37
 */
public interface ISchoolService {
    public List<School> findAll();
}
