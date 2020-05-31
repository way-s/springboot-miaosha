package cn.huanhu.service;

import cn.huanhu.dao.GoodsDao;
import cn.huanhu.entity.MiaoshaGoods;
import cn.huanhu.entity.vo.GoodsVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author m
 * @className GoodsService
 * @description GoodsService
 * @date 2020/5/29
 */
@Service
public class GoodsService {

    private static final Logger logger = LoggerFactory.getLogger(GoodsService.class);

    @Autowired
    private GoodsDao goodsDao;

    public List<GoodsVO> listGoodsVo() {
        return goodsDao.listGoodsVo();
    }

    public GoodsVO getGoodsVoByGoodsId(long id) {
        return goodsDao.getGoodsVoByGoodsId(id);
    }

    public void reduceStock(GoodsVO goodsVO) {
        MiaoshaGoods goods = new MiaoshaGoods();
        goods.setGoodsId(goodsVO.getId());
        goodsDao.reduceStock(goods);
    }
}
