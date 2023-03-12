package com.health.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.health.dao.OrderSettingDao;
import com.health.entity.OrderSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService{
    @Autowired
    private OrderSettingDao orderSettingDao;

    @Override
    public void add(OrderSetting orderSetting) {
        //首先需要判断数据库里有没有设置当前日期的可预约人数
        Integer count = orderSettingDao.findByOrderDate(orderSetting.getOrderDate());
        if (count > 0){
            //如果数据库里面存在当前日期的可预约人数，则修改数据库
            orderSettingDao.editByOrderDate(orderSetting);
        }
        //如果没有，则将数据加入数据库
        orderSettingDao.add(orderSetting);
    }
}
