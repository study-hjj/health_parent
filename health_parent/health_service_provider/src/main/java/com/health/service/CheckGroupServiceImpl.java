package com.health.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.health.dao.CheckGroupDao;
import com.health.entity.CheckGroup;
import com.health.util.PageResult;
import com.health.util.QueryPageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService{
    @Autowired
    private CheckGroupDao checkGroupDao;
    @Override
    public void add(CheckGroup checkGroup, Integer[] ids) {
        //新增检查组
        checkGroupDao.add(checkGroup);
        //将检查组和勾选的检查项关联起来
        if (ids != null && ids.length > 0){
            for (Integer id : ids){
                checkGroupDao.addCheckGroupCheckItem(checkGroup.getId(), id);
            }
        }
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        //当前页码
        Integer currentPage = queryPageBean.getCurrentPage();
        //每页记录数
        Integer pageSize = queryPageBean.getPageSize();
        //查询条件
        String queryString = queryPageBean.getQueryString();
        PageHelper.startPage(currentPage,pageSize);
        Page<CheckGroup> page = checkGroupDao.findPage(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public void delete(Integer id) {
        //首先需要根据id查询出有没有和该检查组关联的检查项
        Integer count = checkGroupDao.findByCheckGroupId(id);
        if (count > 0){
            //如果关联表中有关联检查项，需要先删除关联表中的信息
            checkGroupDao.deleteCheckGroupAndCheckItem(id);
        }
        //删除检查组
        checkGroupDao.delete(id);
    }

    @Override
    public CheckGroup findById(Integer id) {
        return checkGroupDao.findById(id);
    }

    @Override
    public Integer[] findCheckItemIdsByCheckGroupId(Integer id) {
        return checkGroupDao.findCheckItemIdsByCheckGroupId(id);
    }

    @Override
    public void edit(CheckGroup checkGroup, Integer[] ids) {
        checkGroupDao.edit(checkGroup);
        //清理检查组和检查项的关系
        checkGroupDao.deleteCheckGroupAndCheckItem(checkGroup.getId());
        if (ids != null && ids.length > 0){
            for (Integer id : ids){
                checkGroupDao.addCheckGroupCheckItem(checkGroup.getId(),id);
            }
        }
    }

}
