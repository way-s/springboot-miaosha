package cn.huanhu.service;

import cn.huanhu.dao.UserDao;
import cn.huanhu.entity.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author m
 * @className UserService
 * @description UserService
 * @date 2020/5/12
 */
@Service
public class UserService {

    @Resource
    private UserDao userDao;

    /**
     * 查询单条数据
     * @param id id
     * @return user
     */
    public User queryById(Integer id) {
        return userDao.queryById(id);
    }



}
