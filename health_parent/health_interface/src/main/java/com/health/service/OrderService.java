package com.health.service;

import com.health.util.Result;

import java.util.Map;

public interface OrderService {

    Result order(Map map) throws Exception;

    Map findById(Integer id);
}
