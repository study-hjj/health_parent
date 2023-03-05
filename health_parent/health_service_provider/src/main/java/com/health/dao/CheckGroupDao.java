package com.health.dao;

import com.github.pagehelper.Page;
import com.health.entity.CheckGroup;
import org.apache.ibatis.annotations.Param;

public interface CheckGroupDao {
    void add(CheckGroup checkGroup);

    void addCheckGroupCheckItem(@Param("checkgroup_id") Integer checkgroupId, @Param("checkitem_id") Integer checkitemId);

    Page<CheckGroup> findPage(String queryString);

    void delete(Integer id);

    Integer findByCheckGroupId(@Param("checkgroupId") Integer id);

    void deleteCheckGroupAndCheckItem(@Param("checkgroupId") Integer id);

    CheckGroup findById(Integer id);

    Integer[] findCheckItemIdsByCheckGroupId(Integer id);

    void edit(CheckGroup checkGroup);

}
