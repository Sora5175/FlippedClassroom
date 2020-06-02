package com.sora.domain;

import lombok.Data;

import java.util.List;

/**
 * @author Sora
 * @version 1.0
 * @date 2020/3/30 15:36
 */
@Data
public class School {
    private int id;
    private String name;
    private List<Class> classList;
}
