package com.health.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.health.dao.SetmealDao;
import com.health.entity.Setmeal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService{

    @Autowired
    private SetmealDao setmealDao;

    @Override
    public void add(Integer[] checkgroupIds, Setmeal setmeal) {
        //新增套餐
        setmealDao.add(setmeal);
        //将检查组和套餐关联起来
        for (Integer checkgroupId:checkgroupIds){
            setmealDao.addSetmealAndCheckgroup(setmeal.getId(),checkgroupId);
        }
    }
}
