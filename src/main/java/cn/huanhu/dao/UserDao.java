package cn.huanhu.dao;

import cn.huanhu.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName: UserDao
 * @Description: UserDao
 * @author: m
 * @Date: 2020/5/12
 */
@Mapper
public interface UserDao {
    /**
     * 查询单条数据
     * @param id id
     * @return user
     */
    User queryById(Integer id);
}
