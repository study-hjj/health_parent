package com.health.dao;

import com.health.entity.OrderSetting;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderSettingDao {
    Integer findByOrderDate(@Param("orderDate") Date orderDate);

    void editByOrderDate(OrderSetting orderDate);

    void add(OrderSetting orderSetting);

    List<OrderSetting> getOrderSettingByMonth(Map<String, String> map);

    void editReservationsByOrderDate(OrderSetting orderSetting);

    OrderSetting selectByOrderDate(Date date);
}
