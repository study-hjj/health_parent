package com.health.service;


import com.health.entity.CheckGroup;
import com.health.util.PageResult;
import com.health.util.QueryPageBean;

public interface CheckGroupService {
    void add(CheckGroup checkGroup, Integer[] ids);

    PageResult findPage(QueryPageBean queryPageBean);
}
