package com.health.service;


import com.health.entity.Setmeal;
import com.health.util.PageResult;
import com.health.util.QueryPageBean;

public interface SetmealService {
    void add(Integer[] checkgroupIds, Setmeal setmeal);

    PageResult findPage(QueryPageBean queryPageBean);
}
