package com.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.health.constant.MessageConstant;
import com.health.entity.OrderSetting;
import com.health.service.OrderSettingService;
import com.health.util.POIUtils;
import com.health.util.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {
    @Reference
    private OrderSettingService orderSettingService;

    /**
     * @Description: 将excel表中的数据批量导入数据库
     * @author: hjj
     * @date: 2023/3/12
     * @param excelFile
     * @return: com.health.util.Result
     */
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
    /**
     * @Description: 根据当前日期修改可预约人数
     * @author: hjj
     * @date: 2023/3/12
     * @param orderSetting
     * @return: com.health.util.Result
     */
    @RequestMapping("/editNumberByOrderDate")
    public Result editNumberByOrderDate(@RequestBody OrderSetting orderSetting){
        try{
            orderSettingService.editNumberByOrderDate(orderSetting);
            return new Result(true, MessageConstant.ORDERSETTING_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.ORDERSETTING_FAIL);
        }
    }
    @RequestMapping("/getOrderSettingByMonth")
    public Result getOrderSettingByMonth(@RequestParam("month") String month){
        try {
            List<Map<String,Object>> orderSettings = orderSettingService.getOrderSettingByMonth(month);
            return new Result(true,MessageConstant.ORDERSETTING_SUCCESS,orderSettings);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ORDERSETTING_FAIL);
        }
    }
}
