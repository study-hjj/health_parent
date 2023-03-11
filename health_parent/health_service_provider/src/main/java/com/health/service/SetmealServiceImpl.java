package com.health.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.health.constant.RedisConstant;
import com.health.dao.SetmealDao;
import com.health.entity.Setmeal;
import com.health.util.PageResult;
import com.health.util.QueryPageBean;
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

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        PageHelper.startPage(currentPage,pageSize);
        Page<Setmeal> page = setmealDao.findPage(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public Setmeal findById(Integer id) {
        Setmeal setmeal = setmealDao.findById(id);
        return setmeal;
    }

    @Override
    public void edit(Integer[] ids, Setmeal setmeal) {
        setmealDao.edit(setmeal);
        //清理检查组和检查项的关系
        setmealDao.deleteCheckGroupAndCheckItem(setmeal.getId());
        if (ids != null && ids.length > 0){
            for (Integer id : ids){
                setmealDao.addSetmealAndCheckgroup(setmeal.getId(),id);
            }
        }
    }

    @Override
    public void delete(Integer id) {
        //首先需要根据id查询出有没有和该套餐关联的检查组
        Integer count = setmealDao.findBySetmealId(id);
        if (count > 0){
            //如果关联表中有关联检查项，需要先删除关联表中的信息
            setmealDao.deleteSetmealAndCheckGroup(id);
        }
        setmealDao.delete(id);
    }

    @Override
    public Integer[] findCheckgroupIdsBySetmealId(Integer id) {
        return setmealDao.findCheckgroupIdsBySetmealId(id);
    }
}
