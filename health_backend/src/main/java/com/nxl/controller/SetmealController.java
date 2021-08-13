package com.nxl.controller;

import com.alibaba.dubbo.config.annotation.Reference;

import com.nxl.constant.MessageConstant;
import com.nxl.constant.RedisConstant;
import com.nxl.entity.PageResult;
import com.nxl.entity.QueryPageBean;
import com.nxl.entity.Result;
import com.nxl.pojo.Setmeal;
import com.nxl.service.SetmealService;
import com.nxl.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.nio.channels.MulticastChannel;
import java.util.UUID;

/**
 * 体检套餐管理
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    //使用JedisPool操作Redis服务

    @Autowired
    private JedisPool jedisPool;

    /*
    文件上传
     */
    @RequestMapping("/upload")
    public Result upload(@RequestParam("imgFile") MultipartFile imgFile) {
        System.out.println(imgFile);
        String originalFilename=imgFile.getOriginalFilename(); //原始文件名
        int index=  originalFilename.lastIndexOf(".");
        String extention=originalFilename.substring(index);
        String fileName= UUID.randomUUID().toString()+extention;
        try {
            //将文件上传到七牛云服务器上
            QiniuUtils.upload2Qiniu(imgFile.getBytes(),fileName);
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
        return new Result(true,MessageConstant.UPLOAD_SUCCESS,fileName);
    }

    @Reference
    private SetmealService setmealService;

    @RequestMapping("/add")
    public Result add(@RequestBody Setmeal setmeal,Integer[] checkgroudIds){
        try {
            setmealService.add(setmeal,checkgroudIds);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_SETMEAL_FAIL);
        }

        return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
    }

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        return setmealService.findPage(queryPageBean);
    }

}
