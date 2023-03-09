package com.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.health.constant.MessageConstant;
import com.health.constant.RedisConstant;
import com.health.entity.Setmeal;
import com.health.service.SetmealService;
import com.health.util.QiniuUtils;
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
}
