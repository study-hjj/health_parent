package com.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.health.constant.MessageConstant;
import com.health.entity.OrderSetting;
import com.health.service.OrderSettingService;
import com.health.util.DateUtils;
import com.health.util.POIUtils;
import com.health.util.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {
    @Reference
    private OrderSettingService orderSettingService;

    @RequestMapping("/upload")
    public Result upload(@RequestParam("excelFile") MultipartFile excelFile){
        try {
            //获取excel表中的数据
            List<String[]> ordersettings = POIUtils.readExcel(excelFile);
            OrderSetting orderSetting = new OrderSetting();
            for (String[] value : ordersettings){
                orderSetting.setOrderDate(new SimpleDateFormat("yyyy/MM/dd").parse(value[0]));
                orderSetting.setNumber(Integer.parseInt(value[1]));
                //将获取到的数据新增到数据库
                orderSettingService.add(orderSetting);
            }
            return new Result(true,MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
    }
}
