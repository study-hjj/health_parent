package com.health.service;

import com.health.entity.CheckItem;
import com.health.util.PageResult;
import com.health.util.QueryPageBean;

public interface CheckItemService {
    void add(CheckItem checkItem);
    PageResult findPage(QueryPageBean queryPageBean);
    void delete(Integer id);
    CheckItem findById(Integer id);
    void edit(CheckItem checkItem);
    CheckItem[] findAll();
}
