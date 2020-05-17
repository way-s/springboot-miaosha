package cn.huanhu.dao;

import cn.huanhu.entity.MiaoshaUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
     */
    @Select("select * from miaosha_user where id = #{id}")
    public MiaoshaUser getById(@Param("id") long id);

}
