package com.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.health.constant.MessageConstant;
import com.health.entity.Setmeal;
import com.health.service.SetmealService;
import com.health.util.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Reference
    private SetmealService setmealService;
    /**
     * @Description: 获取所有套餐信息
     * @author: hjj
     * @date: 2023/5/25
     * @param
     * @return: com.health.util.Result
     */
    @RequestMapping("/getSetmeal")
    public Result getSetmeal(){
        try {
            List<Setmeal> list = setmealService.findAll();
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }
    /**
     * @Description: 根据套餐id查询套餐详情，包含（套餐基本信息、套餐关联的检查组、检查组关联的检查项）
     * @author: hjj
     * @date: 2023/5/25
     * @param
     * @return:
     */
    @RequestMapping("/findById")
    public Result findById(@RequestParam("id") Integer id){
        try {
            Setmeal setmeal = setmealService.findByIdDetail(id);
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }
}
