package com.nxl.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.nxl.dao.CheckItemDao;
import com.nxl.entity.PageResult;
import com.nxl.entity.QueryPageBean;
import com.nxl.pojo.CheckItem;
import com.nxl.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service(interfaceClass = CheckItemService.class)   //暴露服务
@Transactional  //事务
public class CheckItemServiceImpl implements CheckItemService {

    //注入Dao对象
    @Autowired
    private CheckItemDao checkItemDao;

    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }


    //检查分页查询
    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {


        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();

        //完成分页查询，基于mybatis框架提供的分页助手插件完成
        PageHelper.startPage(currentPage,pageSize);

        //select * from t_checkitem limit 0,10;   currentPage,pageSize对应着这两个数字
        Page<CheckItem> page=checkItemDao.selectByCondition(queryString);

        long total = page.getTotal();
        List<CheckItem> rows =page.getResult();
        return new PageResult(total,rows);
    }

//    @Override
////    public void deleteById(Integer id) {
////        //判断当前检查项是否已经关联到检查组
////        long count=checkItemDao.findCountByCheckItemId(id);
////        if(count>0){
////            //当前检查项已经被关联到检查组了,不能进行删除,不允许删除
////          throw  new RuntimeException("当前的检查项已经关联了其他的检查项，不能进行删除");
////        }
////
////        checkItemDao.deleteById(id);
////    }

    //根据ID删除检查项
    @Override
    public void deleteById(@RequestBody Integer id) {
        //判断当前检查项是否已经关联到检查组
        long count = checkItemDao.findCountByCheckItemId(id);
        if(count > 0){
            //当前检查项已经被关联到检查组，不允许删除
          throw new RuntimeException();
        }
        checkItemDao.deleteById(id);

    }


    @Override
    public void edit(CheckItem checkItem) {
        checkItemDao.edit(checkItem);
    }

    @Override
    public CheckItem findById(Integer id) {
        return checkItemDao.findById(id);
    }

    @Override
    public List<CheckItem> findAll() {

        return checkItemDao.findAll();
    }


}
