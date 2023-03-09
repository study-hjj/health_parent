package com.health.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.health.constant.RedisConstant;
import com.health.dao.SetmealDao;
import com.health.entity.Setmeal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService{

    @Autowired
    private SetmealDao setmealDao;
    @Autowired
    private JedisPool jedisPool;

    @Override
    public void add(Integer[] checkgroupIds, Setmeal setmeal) {
        //新增套餐
        setmealDao.add(setmeal);
        //将检查组和套餐关联起来
        for (Integer checkgroupId:checkgroupIds){
            setmealDao.addSetmealAndCheckgroup(setmeal.getId(),checkgroupId);
        }
        //完成数据库操作后需要将图片名称保存到redis
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,setmeal.getImg());
    }
}
