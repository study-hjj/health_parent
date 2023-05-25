package com.health.dao;

import com.github.pagehelper.Page;
import com.health.entity.Setmeal;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SetmealDao {
    void add(Setmeal setmeal);

    void addSetmealAndCheckgroup(@Param("setmeal_id") Integer id, @Param("checkgroup_id") Integer checkgroupId);

    Page<Setmeal> findPage(String queryString);

    Setmeal findById(@Param("id") Integer id);

    void edit(Setmeal setmeal);

    void deleteCheckGroupAndCheckItem(@Param("setmeal_id") Integer id);

    void delete(@Param("id") Integer id);

    Integer[] findCheckgroupIdsBySetmealId(@Param("setmeal_id") Integer id);

    Integer findBySetmealId(@Param("setmeal_id") Integer id);

    void deleteSetmealAndCheckGroup(@Param("setmeal_id") Integer id);

    List<Setmeal> findAll();

    Setmeal findByIdDetail(Integer id);
}
