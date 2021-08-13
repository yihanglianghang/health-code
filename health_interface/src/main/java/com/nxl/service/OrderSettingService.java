package com.nxl.service;

import com.nxl.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

public interface OrderSettingService {

    public void add(List<OrderSetting> data);

    List<Map> getOrderSettingByMonth(String date);
}
