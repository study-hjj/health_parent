package com.health.service;

import com.health.entity.OrderSetting;

public interface OrderSettingService {
    void add(OrderSetting orderSetting);

    void editNumberByOrderDate(OrderSetting orderSetting);
}
