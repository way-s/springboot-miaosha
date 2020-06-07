package cn.huanhu.dao;

import cn.huanhu.entity.Goods;
import cn.huanhu.entity.MiaoshaGoods;
import cn.huanhu.entity.vo.GoodsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @className GoodsDao
 * @description (Goods)表数据库访问层
 * @author m
 * @date 2020/05/29
 */
@Mapper
public interface GoodsDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Goods queryById(Long id);

    /**
     * 查询所有商品
     * @return GoodsVO
     */
    List<GoodsVO> listGoodsVo();

    /**
     * 根据商品id查询 商品
     * @param goodsId
     * @return GoodsVO
     */
    GoodsVO getGoodsVoByGoodsId(@Param("goodsId") long goodsId);

    /**
     * 减库存并更新库存
     * @param goods
     * @return
     */
    int reduceStock(MiaoshaGoods goods);


}