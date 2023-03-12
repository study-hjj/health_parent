package com.health.service;

import com.health.entity.OrderSetting;

import java.util.List;
import java.util.Map;

public interface OrderSettingService {
    void add(OrderSetting orderSetting);

    void editNumberByOrderDate(OrderSetting orderSetting);

    List<Map<String, Object>> getOrderSettingByMonth(String month);
}
