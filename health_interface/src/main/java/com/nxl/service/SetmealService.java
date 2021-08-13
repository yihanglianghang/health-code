package com.nxl.service;

import com.nxl.entity.PageResult;
import com.nxl.entity.QueryPageBean;
import com.nxl.pojo.Setmeal;

public interface SetmealService {

    public void add(Setmeal setmeal,Integer[] checkgroupIds);

    public  PageResult findPage(QueryPageBean queryPageBean);
}
