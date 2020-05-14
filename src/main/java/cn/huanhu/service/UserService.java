package cn.huanhu.service;

import cn.huanhu.entity.User;

/**
 * @ClassName: UserService
 * @Description: UserService
 * @author: m
 * @Date: 2020/5/12
 */
public interface UserService {

    /**
     * 查询单条数据
     * @param id id
     * @return user
     */
    User queryById(Integer id);

}
