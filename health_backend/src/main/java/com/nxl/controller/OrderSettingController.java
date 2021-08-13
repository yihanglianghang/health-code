package com.nxl.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.nxl.constant.MessageConstant;
import com.nxl.entity.Result;
import com.nxl.pojo.OrderSetting;
import com.nxl.service.OrderSettingService;
import com.nxl.utils.POIUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 预约设置
 */
@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {

    @Reference
    private OrderSettingService orderSettingService;

    //文件上传，实现预约设置数据批量导入

    @RequestMapping("/upload")
    public Result upload(@RequestParam("excelFile") MultipartFile excelFile){

        try {
            List<String[]> list= POIUtils.readExcel(excelFile);
            List<OrderSetting> data=new ArrayList<>();
            for(String[] strings:list){
                String orderDate=strings[0];
                String number=strings[1];
                OrderSetting orderSetting=new OrderSetting(new Date(orderDate),Integer.parseInt(number));
               data.add(orderSetting);
            }

            //通过dubbo远程调用服务实现数据批量导入数据库
             orderSettingService.add(data);
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        } catch (IOException e) {
            e.printStackTrace();
            //文件解析失败
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);

        }
    }

    @RequestMapping("/getOrderSettingByMonth")
    public Result getOrderSettingByMonth(String date){
        try {
        List<Map> list=orderSettingService.getOrderSettingByMonth(date);
            return new Result(true,MessageConstant.GET_ORDERSETTING_SUCCESS,list);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_ORDERSETTING_FAIL);
        }

    }



}
