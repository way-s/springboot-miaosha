package cn.huanhu.service;

import cn.huanhu.entity.BookStore;



/**
 * (BookStore)表服务接口
 *
 * @author makejava
 * @since 2020-05-11
 */
public interface BookStoreService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    BookStore queryById(Integer id);




}