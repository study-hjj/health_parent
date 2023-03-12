package com.health.dao;

import com.health.entity.OrderSetting;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface OrderSettingDao {
    Integer findByOrderDate(@Param("orderDate") Date orderDate);

    void editByOrderDate(OrderSetting orderDate);

    void add(OrderSetting orderSetting);
}
