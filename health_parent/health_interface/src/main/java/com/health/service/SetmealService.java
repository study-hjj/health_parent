package com.health.service;


import com.health.entity.Setmeal;
import com.health.util.PageResult;
import com.health.util.QueryPageBean;

import java.util.List;

public interface SetmealService {
    void add(Integer[] checkgroupIds, Setmeal setmeal);

    PageResult findPage(QueryPageBean queryPageBean);

    Setmeal findById(Integer id);

    void edit(Integer[] ids, Setmeal setmeal);

    void delete(Integer id);

    Integer[] findCheckgroupIdsBySetmealId(Integer id);

    List<Setmeal> findAll();
}
