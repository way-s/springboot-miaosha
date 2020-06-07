package cn.huanhu.dao;

import cn.huanhu.entity.MiaoshaUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @ClassName: MiaoshaUserDao
 * @Description: MiaoshaUserDao
 * @author: m
 * @Date: 2020/5/13
 */
@Mapper
public interface MiaoshaUserDao {

    /**
     * getById
     * @param id id
     * @return MiaoshaUser
     * "select * from miaosha_user where id = #{id}"
     */
    public MiaoshaUser getById(@Param("id") long id);

    /**
     * 注册
     * @param user user
     * @return int
     */
    int insert(MiaoshaUser user);

    /**
     * 更新密码
     * @param toBeUpdate
     * @return
     */
    int update(MiaoshaUser toBeUpdate);
}
