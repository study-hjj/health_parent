package com.health.controller;

import com.health.util.POIUtils;
import com.health.util.Result;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {

    @RequestMapping("/upload")
    public Result upload(@RequestParam("excelFile") MultipartFile excelFile){
        try {
            //获取excel表中的数据
            List<String[]> strings = POIUtils.readExcel(excelFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
