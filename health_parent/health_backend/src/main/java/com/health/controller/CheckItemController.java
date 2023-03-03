package com.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.health.constant.MessageConstant;
import com.health.entity.CheckItem;
import com.health.service.CheckItemService;
import com.health.util.PageResult;
import com.health.util.QueryPageBean;
import com.health.util.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/checkitem")
public class CheckItemController {
    //到zookeeper中心去查找服务
    @Reference
    private CheckItemService checkItemService;
    /**
     * @Description: 新增检查项
     * @author: hjj
     * @date: 2023/3/1
     * @param checkItem
     * @return: com.health.util.Result
     */
    @RequestMapping("/add")
    public Result add(@RequestBody CheckItem checkItem){
        try{
            checkItemService.add(checkItem);//发送请求
            return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }
    }
    /**
     * @Description: 分页查询
     * @author: hjj
     * @date: 2023/3/1
     * @param queryPageBean
     * @return: com.health.util.PageResult
     */
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult page = checkItemService.findPage(queryPageBean);
        return page;
    }
    /**
     * @Description: 删除检查项
     * @author: hjj
     * @date: 2023/3/1
     * @param id
     * @return: com.health.util.PageResult
     */
    @RequestMapping("/delete")
    public Result delete(Integer id){
        try{
            checkItemService.delete(id);//发送请求
            return new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS);
        }catch (Exception e){
            String message = e.getMessage();
            e.printStackTrace();
            return new Result(false, message);
        }
    }
    /**
     * @Description: 根据id查询检查项
     * @author: hjj
     * @date: 2023/3/1
     * @param id
     * @return: com.health.util.PageResult
     */
    @RequestMapping("/findById")
    public Result findById(Integer id){
        try{
            CheckItem checkItem = checkItemService.findById(id);//发送请求
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItem);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }
    /**
     * @Description: 根据id编辑检查项
     * @author: hjj
     * @date: 2023/3/1
     * @param checkItem
     * @return: com.health.util.PageResult
     */
    @RequestMapping("/edit")
    public Result edit(@RequestBody CheckItem checkItem){
        try{
            checkItemService.edit(checkItem);//发送请求
            return new Result(true, MessageConstant.EDIT_CHECKITEM_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_CHECKITEM_FAIL);
        }
    }
    /**
     * @Description: 查询所有检查项信息
     * @author: hjj
     * @date: 2023/3/3
     * @param
     * @return: com.health.util.Result
     */
    @RequestMapping("/findAll")
    public Result findAll(){
        try{
            CheckItem[] checkItems = checkItemService.findAll();//发送请求
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItems);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }
}
