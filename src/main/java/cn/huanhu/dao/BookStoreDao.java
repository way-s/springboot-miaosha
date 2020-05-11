package cn.huanhu.dao;

import cn.huanhu.entity.BookStore;
import org.apache.ibatis.annotations.Mapper;


/**
 * (BookStore)表数据库访问层
 *
 * @author makejava
 * @since 2020-05-11
 */
@Mapper
public interface BookStoreDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    BookStore queryById(Integer id);



}