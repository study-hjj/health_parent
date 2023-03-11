package com.health.dao;

import com.health.entity.Setmeal;
import org.apache.ibatis.annotations.Param;

public interface SetmealDao {
    void add(Setmeal setmeal);

    void addSetmealAndCheckgroup(@Param("setmeal_id") Integer id, @Param("checkgroup_id") Integer checkgroupId);
}
