package com.health.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.health.dao.OrderSettingDao;
import com.health.entity.OrderSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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

    @Override
    public void editNumberByOrderDate(OrderSetting orderSetting) {
        orderSettingDao.editByOrderDate(orderSetting);
    }

    @Override
    public List<Map<String, Object>> getOrderSettingByMonth(String month) {
        String start = month + "-1";//2023-3-1
        String end = month + "-31";//2023-3-31
        Map<String,String> map = new HashMap<>();
        map.put("start",start);
        map.put("end",end);
        List<Map<String, Object>> list = new ArrayList<>();
        List<OrderSetting> orderSettings = orderSettingDao.getOrderSettingByMonth(map);
        for (OrderSetting orderSetting : orderSettings){
            Map<String, Object> data = new LinkedHashMap<>();
            data.put("data",orderSetting.getOrderDate().getDate());
            data.put("number",orderSetting.getNumber());
            data.put("reservations",orderSetting.getReservations());
            list.add(data);
        }
        return list;
    }
}
