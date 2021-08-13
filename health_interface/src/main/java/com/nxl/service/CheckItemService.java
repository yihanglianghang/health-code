package com.nxl.service;

import com.nxl.entity.PageResult;
import com.nxl.entity.QueryPageBean;
import com.nxl.pojo.CheckItem;

import java.util.List;

//服务接口，发现服务
public interface CheckItemService {

    public void add(CheckItem checkItem);

    public PageResult pageQuery(QueryPageBean queryPageBean);

    void deleteById(Integer id);

    void edit(CheckItem checkItem);

    public  CheckItem findById(Integer id);


    public List<CheckItem> findAll();
}
