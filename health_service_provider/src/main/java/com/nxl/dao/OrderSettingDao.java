package com.nxl.dao;

import com.nxl.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderSettingDao {

    public void add(OrderSetting orderSetting);

    public void editNumberByOrderDate(OrderSetting orderSetting);

    public long findCountByOrderDate(Date orderDate);

    List<OrderSetting> getOrderSettingByMonth(Map map);
}
