package com.nxl.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.nxl.constant.RedisConstant;
import com.nxl.dao.SetmealDao;
import com.nxl.entity.PageResult;
import com.nxl.entity.QueryPageBean;
import com.nxl.pojo.Setmeal;
import com.nxl.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.Map;

@Service(interfaceClass=SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealDao setmealDao;

    @Autowired
    private JedisPool jedisPool;

    //新增套餐信息，同时需要关联检查组
    @Override
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        setmealDao.add(setmeal);
        Integer setmealId = setmeal.getId();
        this.setSetmalAndCheckgroup(setmealId,checkgroupIds);

        //将图片名称保存到Redis集合中
        String fileName=setmeal.getImg();
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,fileName);
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {

        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
      //  Page<Object> objects = PageHelper.startPage(currentPage, pageSize);错误代码
        PageHelper.startPage(currentPage,pageSize);
        //调用Dao中的方法
        Page<Setmeal> page= setmealDao.findByCondition(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }


    //设置套餐和检查组多对多关系，操作t_setmeal_checkgroup

    public void setSetmalAndCheckgroup(Integer setmealId,Integer[] checkgroupIds){
        if(checkgroupIds !=null && checkgroupIds.length>0){
            for (Integer checkgroudId:checkgroupIds){
                Map<String,Integer> map=new HashMap<>();
                map.put("setmealId",setmealId);
                map.put("checkgroudId",checkgroudId);
                setmealDao.setSetmealAndCheckGroup(map);
            }
        }
    }

}
