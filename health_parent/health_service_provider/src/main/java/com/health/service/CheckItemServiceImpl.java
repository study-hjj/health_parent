package com.health.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.health.dao.CheckItemDao;
import com.health.entity.CheckItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service(interfaceClass = CheckItemService.class)
@Transactional
public class CheckItemServiceImpl implements CheckItemService{
    @Autowired
    private CheckItemDao checkItemDao;

    //新增检查项
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }
}
