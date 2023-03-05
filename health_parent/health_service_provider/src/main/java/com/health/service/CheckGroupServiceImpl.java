package com.health.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.health.dao.CheckGroupDao;
import com.health.entity.CheckGroup;
import com.health.entity.CheckItem;
import com.health.util.PageResult;
import com.health.util.QueryPageBean;
import com.sun.tools.corba.se.idl.toJavaPortable.Helper;
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

}
