package cn.huanhu.service.impl;

import cn.huanhu.dao.UserDao;
import cn.huanhu.entity.User;
import cn.huanhu.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author m
 * @className UserServiceImpl
 * @description UserServiceImpl
 * @date 2020/5/12
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Override
    public User queryById(Integer id) {
        return userDao.queryById(id);
    }


}
