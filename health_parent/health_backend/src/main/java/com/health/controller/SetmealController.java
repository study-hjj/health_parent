package com.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.health.constant.MessageConstant;
import com.health.constant.RedisConstant;
import com.health.entity.Setmeal;
import com.health.service.SetmealService;
import com.health.util.PageResult;
import com.health.util.QiniuUtils;
import com.health.util.QueryPageBean;
import com.health.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.util.UUID;


@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Reference
    private SetmealService setmealService;
    @Autowired
    private JedisPool jedisPool;
    /**
     * @Description: 新增套餐成功
     * @author: hjj
     * @date: 2023/3/8
     * @param checkgroupIds
     * @param setmeal
     * @return: com.health.util.Result
     */
    @RequestMapping("/add")
    public Result add(@RequestParam("checkgroupIds") Integer[] checkgroupIds, @RequestBody Setmeal setmeal){
        try {
            setmealService.add(checkgroupIds,setmeal);
            return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_SETMEAL_FAIL);
        }
    }
    /**
     * @Description: 将上传的图片保存在七牛云服务和redis中
     * @author: hjj
     * @date: 2023/3/11
     * @param imgFile
     * @return: com.health.util.Result
     */
    @RequestMapping("/upload")
    public Result upload(@RequestParam("imgFile")MultipartFile imgFile){
        //获取原始文件名
        String originalFilename = imgFile.getOriginalFilename();
        //为避免图片名称重复，给图片生成新的名称，并加上原来的后缀名
        int index = originalFilename.lastIndexOf(".");
        String suffix = originalFilename.substring(index);
        String fileName = UUID.randomUUID().toString() + suffix;
        //将图片保存到七牛云
        try {
            QiniuUtils.upload2Qiniu(imgFile.getBytes(),fileName);
            //文件上传成功后，将文件保存在redis中
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,fileName);
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS,fileName);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.PIC_UPLOAD_FAIL);
        }
    }
    /**
     * @Description: 分页查询
     * @author: hjj
     * @date: 2023/3/11
     * @param queryPageBean
     * @return: com.health.util.Result
     */
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        return setmealService.findPage(queryPageBean);
    }
    /**
     * @Description: 根据id查询套餐
     * @author: hjj
     * @date: 2023/3/11
     * @param id
     * @return: com.health.util.Result
     */
    @RequestMapping("/findById")
    public Result findById(@RequestParam("id") Integer id){
        try {
            Setmeal setmeal = setmealService.findById(id);
            return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }
    /**
     * @Description: 编辑套餐
     * @author: hjj
     * @date: 2023/3/11
     * @param ids
     * @param setmeal
     * @return: com.health.util.Result
     */
    @RequestMapping("/edit")
    public Result edit(@RequestParam("checkitemIds") Integer[] ids,@RequestBody Setmeal setmeal){
        try {
            setmealService.edit(ids,setmeal);
            return new Result(true,MessageConstant.EDIT_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_SETMEAL_FAIL);
        }
    }
    /**
     * @Description: 删除套餐
     * @author: hjj
     * @date: 2023/3/11
     * @param id
     * @return: com.health.util.Result
     */
    @RequestMapping("/delete")
    public Result delete(@RequestParam("id") Integer id){
        try {
            setmealService.delete(id);
            return new Result(true,MessageConstant.DELETE_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_SETMEAL_FAIL);
        }
    }
    /**
     * @Description: 根据套餐id查询检查组
     * @author: hjj
     * @date: 2023/3/11
     * @param id
     * @return: com.health.util.Result
     */
    @RequestMapping("/findCheckgroupIdsBySetmealId")
    public Result findCheckgroupIdsBySetmealId(@RequestParam("setmealId") Integer id){
        try {
            Integer[] checkgroupIds = setmealService.findCheckgroupIdsBySetmealId(id);
            return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,checkgroupIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }
}
