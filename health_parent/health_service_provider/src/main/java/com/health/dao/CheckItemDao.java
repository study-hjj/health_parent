package com.health.dao;

import com.github.pagehelper.Page;
import com.health.entity.CheckItem;

public interface CheckItemDao {
    void add(CheckItem checkItem);
    Page<CheckItem> findByCondition(String queryString);
    void delete(Integer id);
    CheckItem findById(Integer id);
    void edit(CheckItem checkItem);
    CheckItem[] findAll();
}
