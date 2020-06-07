package cn.huanhu.dao;

import cn.huanhu.entity.MiaoshaOrder;
import cn.huanhu.entity.OrderInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectKey;

/**
 * @ClassName: OrderDao
 * @Description: OrderDao
 * @author: m
 * @Date: 2020/5/30
 */
@Mapper
public interface OrderDao {
    /**
     * 根据用户id和商品id查询订单信息
     * @param userId 用户id
     * @param goodsId 商品id
     * @return 订单信息
     */
    OrderInfo getMiaoshaOrderByUserIdGoodsId(@Param("userId")long userId,@Param("goodsId") long goodsId);

    /**
     * 插入订单信息
     *
     * @SelectKey 返回最近一次插入的id
     * @param orderInfo 订单信息
     * @return long
     */
    @SelectKey(keyColumn = "id",keyProperty = "id",resultType = long.class,before = false,statement = "SELECT LAST_INSERT_ID()")
    long insert(OrderInfo orderInfo);

    /**
     * 插入秒杀的商品表
     * @param miaoshaOrder
     * @return
     */
    int insertMiaoshaOrder(MiaoshaOrder miaoshaOrder);
}
