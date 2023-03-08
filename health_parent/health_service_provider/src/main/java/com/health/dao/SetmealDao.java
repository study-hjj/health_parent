package com.health.dao;

import com.health.entity.Setmeal;

public interface SetmealDao {
    void add(Setmeal setmeal);

    void addSetmealAndCheckgroup(Integer id, Integer checkgroupId);
}
