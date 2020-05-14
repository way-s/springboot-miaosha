package cn.huanhu.controller;

import cn.huanhu.config.redis.prefix.UserKey;
import cn.huanhu.entity.BookStore;
import cn.huanhu.entity.User;
import cn.huanhu.service.BookStoreService;
import cn.huanhu.service.UserService;
import cn.huanhu.service.impl.RedisServiceImpl;
import cn.huanhu.utils.result.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author m
 * @className BookStoreController
 * @description BookStoreController
 * @date 2020/5/11
 */
@RestController
@RequestMapping("bookStore/")
public class BookStoreController {

    @Resource(name = "bookStoreService")
    private BookStoreService bookStoreService;

    @Resource(name = "userService")
    private UserService userService;

    @Resource
    private RedisServiceImpl redisService;

    /**
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public  @ResponseBody
    Result<BookStore> selectOne(Integer id) {
        BookStore bookStore=this.bookStoreService.queryById(id);
        return Result.success(bookStore);
    }

    @RequestMapping(value = "redis/get")
    public @ResponseBody
    Result<BookStore> redisGet(){
        BookStore bookStore = redisService.get(UserKey.getById,"1", BookStore.class);
        return Result.success(bookStore);
    }


    @GetMapping(value = "redis/set")
    public  @ResponseBody
    Result<Boolean> getU(){
        User user=new User();
        user.setEmail("25525252@qq.com");
        user.setName("中山");
        boolean bool=redisService.set(UserKey.getById,"8",user);
        return Result.success(bool);
    }

}