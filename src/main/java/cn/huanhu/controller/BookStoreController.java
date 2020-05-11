package cn.huanhu.controller;

import cn.huanhu.entity.BookStore;
import cn.huanhu.service.BookStoreService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * (BookStore)表控制层
 *
 * @author m
 * @since 2020-05-11
 */
@RestController
@RequestMapping("bookStore")
public class BookStoreController {
    /**
     * 服务对象
     */
    @Resource
    private BookStoreService bookStoreService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    @ResponseBody
    public BookStore selectOne(Integer id) {
        return this.bookStoreService.queryById(id);
    }

}