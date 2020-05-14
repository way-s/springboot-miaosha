package cn.huanhu.service.impl;

import cn.huanhu.dao.BookStoreDao;
import cn.huanhu.entity.BookStore;
import cn.huanhu.service.BookStoreService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * (BookStore)表服务实现类
 *
 * @author makejava
 * @since 2020-05-11
 */
@Service("bookStoreService")
public class BookStoreServiceImpl implements BookStoreService {
    @Resource
    private BookStoreDao bookStoreDao;

    /**
     * 通过ID查询单条数据
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public BookStore queryById(Integer id) {
        return this.bookStoreDao.queryById(id);
    }

}