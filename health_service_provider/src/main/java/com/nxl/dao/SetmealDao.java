package com.nxl.dao;

import com.github.pagehelper.Page;
import com.nxl.pojo.Setmeal;

import java.util.Map;

public interface SetmealDao {
    public void add(Setmeal setmeal);
    public void setSetmealAndCheckGroup(Map map);
    Page<Setmeal> findByCondition(String queryString);
}
