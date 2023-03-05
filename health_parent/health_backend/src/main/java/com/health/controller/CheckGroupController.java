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
}
