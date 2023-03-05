package com.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.health.constant.MessageConstant;
import com.health.entity.CheckGroup;
import com.health.service.CheckGroupService;
import com.health.util.PageResult;
import com.health.util.QueryPageBean;
import com.health.util.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 检查组项目
 * @author: hjj
 * @date: 2023/3/3
 */
@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {
    @Reference
    private CheckGroupService checkGroupService;
    /**
     * @Description: 新增检查组
     * @author: hjj
     * @date: 2023/3/5
     * @param ids
     * @param checkGroup
     * @return: com.health.util.Result
     */
    @RequestMapping("/add")
    public Result add(@RequestParam("checkitemIds") Integer[] ids, @RequestBody CheckGroup checkGroup){
        try {
            //新建一个检查组
            checkGroupService.add(checkGroup,ids);
            return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_CHECKGROUP_FAIL);
        }
    }
    /**
     * @Description: 检查组分页查询
     * @author: hjj
     * @date: 2023/3/5
     * @param
     * @return: com.health.util.PageResult
     */
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        return checkGroupService.findPage(queryPageBean);
    }
    /**
     * @Description: 删除检查组
     * @author: hjj
     * @date: 2023/3/5
     * @param id
     * @return: com.health.util.Result
     */
    @RequestMapping("/delete")
    public Result delete(@RequestParam("id") Integer id){
        try {
            checkGroupService.delete(id);
            return new Result(true,MessageConstant.DELETE_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_CHECKGROUP_FAIL);
        }
    }
    /**
     * @Description: 根据id进行数据查询
     * @author: hjj
     * @date: 2023/3/5
     * @param id
     * @return: com.health.util.Result
     */
    @RequestMapping("findById")
    public Result findById(@RequestParam("id") Integer id){
        try {
            CheckGroup checkGroup = checkGroupService.findById(id);
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroup);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }
    @RequestMapping("/findCheckItemIdsByCheckGroupId")
    public Result findCheckItemIdsByCheckGroupId(@RequestParam("checkgroupId") Integer id){
        try {
            Integer[] checkItemId = checkGroupService.findCheckItemIdsByCheckGroupId(id);
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkItemId);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }
    /**
     * @Description: 编辑检查组
     * @author: hjj
     * @date: 2023/3/5
     * @param
     * @return: com.health.util.Result
     */
    @RequestMapping("/edit")
    public Result edit(@RequestParam("checkitemIds") Integer[] ids, @RequestBody CheckGroup checkGroup){
        try {
            //新建一个检查组
            checkGroupService.edit(checkGroup,ids);
            return new Result(true, MessageConstant.EDIT_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_CHECKGROUP_FAIL);
        }
    }
}
